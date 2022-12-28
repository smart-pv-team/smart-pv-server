package com.adapters.inbound.http.management;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AlgorithmTypeDto(String algorithmType) {

  public AlgorithmTypeDto(@JsonProperty("algorithmType") String algorithmType) {
    this.algorithmType = algorithmType;
  }

}