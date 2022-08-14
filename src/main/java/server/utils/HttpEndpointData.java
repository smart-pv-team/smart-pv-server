package server.utils;

import measurement.parsers.ResponseType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public record HttpEndpointData(String description, Action action, String endpoint,
                               HttpMethod httpMethod, HttpHeaders httpHeaders,
                               ResponseType responseClass) {

}

