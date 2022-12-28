package com.domain.model.management.farm;

import lombok.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Builder
public record HttpEndpointData(String description, String action, String endpoint,
                               HttpMethod httpMethod, HttpHeaders httpHeaders,
                               ResponseType responseClass, String body) {

}

