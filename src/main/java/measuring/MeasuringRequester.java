package measuring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Slf4j
@Component
public class MeasuringRequester {
    private final RestTemplateBuilder restTemplateBuilder;

    public MeasuringRequester() {
        this.restTemplateBuilder = new RestTemplateBuilder();
    }

    public MeasurementResponse getMeasurement(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return sendRequest(url, headers).getBody();
    }

    public ResponseEntity<MeasurementResponse> sendRequest(String url, HttpHeaders headers) {
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<MeasurementResponse> response = restTemplateBuilder.build().exchange(
                url,
                HttpMethod.GET,
                request,
                MeasurementResponse.class
        );
        return response;
    }

}
