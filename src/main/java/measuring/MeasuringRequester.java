package measuring;

import lombok.extern.slf4j.Slf4j;
import measuring.parsers.Response;
import measuring.parsers.StringToResponseClassParser;
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
public class MeasuringRequester {
  private final StringToResponseClassParser stringToResponseClassParser;
  private final RestTemplate restTemplate;
  @Autowired
  public MeasuringRequester(
      RestTemplateBuilder restTemplateBuilder,
      StringToResponseClassParser stringToResponseClassParser
  ) {
    this.stringToResponseClassParser = stringToResponseClassParser;
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    this.restTemplate = restTemplateBuilder.build();
    this.restTemplate.setRequestFactory(requestFactory);
  }

  public Response getMeasurement(MeasuringDeviceEntity measuringDeviceEntity)
      throws ClassNotFoundException {
    HttpEndpointData httpEndpointData = measuringDeviceEntity.endpoints().stream()
        .filter((e) -> e.action().equals(Action.READ)).findFirst()
        .orElseThrow(NullPointerException::new);
    String url = measuringDeviceEntity.ipAddress().concat(httpEndpointData.endpoint());

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
