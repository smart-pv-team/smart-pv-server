package com.domain.model.management.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rule {

  private String id;
  private String intervalId;
  private String deviceId;
  private String action;
}
