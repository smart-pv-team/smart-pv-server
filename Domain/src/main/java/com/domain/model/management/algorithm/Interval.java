package com.domain.model.management.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Interval {

  private String id;
  private Float lowerBound;
  private Float upperBound;
  private String farmId;
}
