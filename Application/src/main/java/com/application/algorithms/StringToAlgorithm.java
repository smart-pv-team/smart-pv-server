package com.application.algorithms;

import com.domain.model.farm.AlgorithmType;
import com.domain.ports.farm.Algorithm;
import org.springframework.stereotype.Component;


@Component
public class StringToAlgorithm {

  public static Algorithm stringToAlgorithm(AlgorithmType algorithmType) {
    return switch (algorithmType) {
      case POWER_PRIORITY -> new PowerPriorityAlgorithm();
      case POWER_TIME_PRIORITY -> new PowerTimeAlgorithm();
      case RANDOM -> new PowerRoundRobinAlgorithm();
      case INTERVAL -> new IntervalAlgorithm();
    };
  }

}