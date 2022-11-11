package com.adapters.outbound.persistence.farm;

import com.adapters.outbound.http.devices.ResponseType;
import com.domain.model.actions.Action;
import com.domain.model.farm.DeviceType;
import com.domain.model.farm.HttpEndpointData;
import lombok.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

@Builder
public record HttpEndpointDataEntity(String description, Action action, String endpoint,
                                     HttpMethod httpMethod, HttpHeaders httpHeaders,
                                     ResponseType responseClass) {

  public static HttpEndpointDataEntity fromDomain(HttpEndpointData httpEndpointData) {
    return HttpEndpointDataEntity.builder()
        .action(httpEndpointData.action())
        .endpoint(httpEndpointData.endpoint())
        .description(httpEndpointData.description())
        .httpHeaders(httpEndpointData.httpHeaders())
        .httpMethod(httpEndpointData.httpMethod())
        .responseClass(ResponseType.valueOf(httpEndpointData.responseClass().toString()))
        .build();
  }

  public static HttpEndpointData toDomain(HttpEndpointDataEntity httpEndpointData) {
    return HttpEndpointData.builder()
        .action(httpEndpointData.action())
        .endpoint(httpEndpointData.endpoint())
        .description(httpEndpointData.description())
        .httpHeaders(httpEndpointData.httpHeaders())
        .httpMethod(httpEndpointData.httpMethod())
        .responseClass(DeviceType.valueOf(httpEndpointData.responseClass().toString()))
        .build();
  }

}

