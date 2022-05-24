package managing;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Component
public class ManagingRequester {
    private final RestTemplateBuilder restTemplateBuilder;

    public ManagingRequester() { this.restTemplateBuilder = new RestTemplateBuilder(); }

    public ConsumerDeviceParametersResponse getDeviceParameters(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return sendDeviceParametersRequest(url, headers).getBody();
    }

    public ConsumerDeviceStatusResponse getDeviceStatus(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return sendDeviceStatusRequest(url, headers).getBody();
    }

    public void sendDeviceStatus(String url, boolean status) {
        ConsumerDeviceStatusResponse payload = new ConsumerDeviceStatusResponse(status);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(payload, headers);
        restTemplateBuilder.build().exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
        );
    }

    public ResponseEntity<ConsumerDeviceParametersResponse> sendDeviceParametersRequest(String url, HttpHeaders headers) {
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<ConsumerDeviceParametersResponse> response = restTemplateBuilder.build().exchange(
                url,
                HttpMethod.GET,
                request,
                ConsumerDeviceParametersResponse.class
        );
        return response;
    }

    public ResponseEntity<ConsumerDeviceStatusResponse> sendDeviceStatusRequest(String url, HttpHeaders headers) {
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<ConsumerDeviceStatusResponse> response = restTemplateBuilder.build().exchange(
                url,
                HttpMethod.GET,
                request,
                ConsumerDeviceStatusResponse.class
        );
        return response;
    }
}
