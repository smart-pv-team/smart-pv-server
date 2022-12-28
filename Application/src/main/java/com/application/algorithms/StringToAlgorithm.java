package com.application.algorithms;

import com.domain.model.management.farm.AlgorithmType;
import com.domain.ports.management.farm.algorithm.Algorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class StringToAlgorithm {

  private final PowerTimeAlgorithm powerTimeAlgorithm;
  private final PowerPriorityAlgorithm powerPriorityAlgorithm;
  private final RandomAlgorithm randomAlgorithm;
  private final IntervalAlgorithm intervalAlgorithm;

  public Algorithm stringToAlgorithm(AlgorithmType algorithmType) {
    return switch (algorithmType) {
      case POWER_PRIORITY -> powerPriorityAlgorithm;
      case POWER_TIME_PRIORITY -> powerTimeAlgorithm;
      case RANDOM -> randomAlgorithm;
      case INTERVAL -> intervalAlgorithm;
    };
  }

}