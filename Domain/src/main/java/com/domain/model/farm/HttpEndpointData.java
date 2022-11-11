package com.domain.model.farm;

import com.domain.model.actions.Action;
import lombok.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Builder
public record HttpEndpointData(String description, Action action, String endpoint,
                               HttpMethod httpMethod, HttpHeaders httpHeaders,
                               DeviceType responseClass) {

}

