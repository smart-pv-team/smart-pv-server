package measurement;

import lombok.extern.slf4j.Slf4j;
import measurement.parsers.Response;
import measurement.parsers.StringToResponseClassParser;
import measurement.persistence.device.MeasurementDeviceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import server.utils.Action;
import server.utils.HttpEndpointData;

@Slf4j
@Component
public class MeasurementRequester {

  private final StringToResponseClassParser stringToResponseClassParser;
  private final RestTemplate restTemplate;

  @Autowired
  public MeasurementRequester(
      RestTemplateBuilder restTemplateBuilder,
      StringToResponseClassParser stringToResponseClassParser
  ) {
    this.stringToResponseClassParser = stringToResponseClassParser;
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    this.restTemplate = restTemplateBuilder.build();
    this.restTemplate.setRequestFactory(requestFactory);
  }

  public Response getMeasurement(MeasurementDeviceEntity measurementDeviceEntity)
      throws ClassNotFoundException {
    HttpEndpointData httpEndpointData = measurementDeviceEntity.endpoints().stream()
        .filter((e) -> e.action().equals(Action.READ)).findFirst()
        .orElseThrow(NullPointerException::new);
    String url = measurementDeviceEntity.ipAddress().concat(httpEndpointData.endpoint());

    return sendRequest(
        url,
        httpEndpointData.httpHeaders(),
        httpEndpointData.httpMethod(),
        stringToResponseClassParser.stringToResponseClass(httpEndpointData.responseClass())
    ).getBody();

  }

  public ResponseEntity<Response> sendRequest(String url, HttpHeaders headers,
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
