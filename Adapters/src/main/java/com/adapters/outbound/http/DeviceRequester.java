package com.adapters.outbound.http;

import com.adapters.outbound.http.devices.ConsumptionOnOff;
import com.adapters.outbound.http.devices.ConsumptionRead;
import com.adapters.outbound.http.devices.MeasurementRead;
import com.adapters.outbound.http.devices.Response;
import com.adapters.outbound.http.devices.ResponseTypeAdapter;
import com.adapters.outbound.persistence.management.farm.FarmRepositoryImpl;
import com.domain.model.actions.Action;
import com.domain.model.actions.ConsumptionOnOffAction;
import com.domain.model.actions.ConsumptionReadAction;
import com.domain.model.actions.MeasurementReadAction;
import com.domain.model.consumption.ConsumptionDevice;
import com.domain.model.management.farm.Device;
import com.domain.model.management.farm.HttpEndpointData;
import com.domain.model.measurement.MeasurementDevice;
import com.domain.ports.management.farm.DeviceGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class DeviceRequester implements DeviceGateway {

  private final RestTemplate restTemplate;
  private final FarmRepositoryImpl farmRepository;

  @Autowired
  public DeviceRequester(RestTemplateBuilder restTemplateBuilder, FarmRepositoryImpl farmRepository) {
    this.farmRepository = farmRepository;
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    this.restTemplate = restTemplateBuilder.build();
    this.restTemplate.setRequestFactory(requestFactory);
  }

  @Override
  public void request(Device device, Action action) {
    HttpEndpointData httpEndpointData = farmRepository
        .getDeviceById(device.getId())
        .map(Device::getEndpoints)
        .orElseThrow(NullPointerException::new)
        .stream()
        .filter(endpoint -> endpoint.action().equals(action.toString()))
        .findFirst()
        .orElseThrow(NullPointerException::new);

    String url = device.getIpAddress().concat(httpEndpointData.endpoint());

    sendRequest(
        url,
        httpEndpointData.httpHeaders(),
        httpEndpointData.httpMethod(),
        ResponseTypeAdapter.stringToResponseClass(
            ResponseTypeAdapter.valueOf(httpEndpointData.responseClass().toString()))
    );
  }

  @Override
  public ConsumptionReadAction requestDevicesStatus(ConsumptionDevice consumptionDevice) {
    ConsumptionRead consumptionRead = sendRequestToDevice(
        consumptionDevice,
        Action.READ.toString()
    );
    return consumptionRead.toDomain(consumptionDevice);
  }

  @Override
  public MeasurementReadAction requestMeasurement(MeasurementDevice measurementDevice) {
    MeasurementRead measurementRead = sendRequestToDevice(
        measurementDevice,
        Action.READ.toString()
    );
    return measurementRead.toDomain(measurementDevice);
  }

  @Override
  public ConsumptionOnOffAction requestOnOff(ConsumptionDevice consumptionDevice, Boolean turnOn) {
    ConsumptionOnOff consumptionOnOff = sendRequestToDevice(
        consumptionDevice,
        (turnOn ? Action.TURN_ON : Action.TURN_OFF).toString()
    );
    return consumptionOnOff.toDomain(consumptionDevice);
  }

  @Override
  public void requestAction(ConsumptionDevice consumptionDevice, String action) {
    sendRequestToDevice(consumptionDevice, action);
  }

  private <T> T sendRequestToDevice(Device device, String action) {
    HttpEndpointData httpEndpointData = farmRepository
        .getDeviceById(device.getId())
        .map(Device::getEndpoints)
        .orElseThrow(NullPointerException::new)
        .stream()
        .filter(endpoint -> endpoint.action().equals(action))
        .findFirst()
        .orElseThrow(NullPointerException::new);

    String url = device.getIpAddress().concat(httpEndpointData.endpoint());

    return (T) sendRequest(
        url,
        httpEndpointData.httpHeaders(),
        httpEndpointData.httpMethod(),
        ResponseTypeAdapter.stringToResponseClass(ResponseTypeAdapter.fromDomain(httpEndpointData.responseClass()))
    ).getBody();

  }

  private ResponseEntity<Response> sendRequest(String url, HttpHeaders headers,
      HttpMethod httpMethod, Class responseClass) {
    HttpEntity request = new HttpEntity(headers);
    return restTemplate.exchange(
        url,
        httpMethod,
        request,
        responseClass
    );
  }
}
