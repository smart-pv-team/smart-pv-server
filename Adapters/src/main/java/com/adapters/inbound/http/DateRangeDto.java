package com.adapters.inbound.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public record DateRangeDto(Date startDate, Date endDate) {

  public DateRangeDto(@JsonProperty("startDate") Date startDate, @JsonProperty("endDate") Date endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

}