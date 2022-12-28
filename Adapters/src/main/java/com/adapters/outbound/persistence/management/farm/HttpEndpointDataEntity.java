package com.adapters.outbound.persistence.management.farm;

import com.adapters.outbound.http.devices.ResponseTypeAdapter;
import com.domain.model.management.farm.HttpEndpointData;
import com.domain.model.management.farm.ResponseType;
import lombok.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Builder
public record HttpEndpointDataEntity(String description, String action, String endpoint,
                                     HttpMethod httpMethod, HttpHeaders httpHeaders,
                                     ResponseTypeAdapter responseClass) {

  public static HttpEndpointDataEntity fromDomain(HttpEndpointData httpEndpointData) {
    return HttpEndpointDataEntity.builder()
        .action(httpEndpointData.action())
        .endpoint(httpEndpointData.endpoint())
        .description(httpEndpointData.description())
        .httpHeaders(httpEndpointData.httpHeaders())
        .httpMethod(httpEndpointData.httpMethod())
        .responseClass(ResponseTypeAdapter.valueOf(httpEndpointData.responseClass().toString()))
        .build();
  }

  public static HttpEndpointData toDomain(HttpEndpointDataEntity httpEndpointData) {
    return HttpEndpointData.builder()
        .action(httpEndpointData.action())
        .endpoint(httpEndpointData.endpoint())
        .description(httpEndpointData.description())
        .httpHeaders(httpEndpointData.httpHeaders())
        .httpMethod(httpEndpointData.httpMethod())
        .responseClass(ResponseType.valueOf(httpEndpointData.responseClass().toString()))
        .build();
  }

}

