package com.application.algorithms;

import com.domain.model.farm.AlgorithmType;
import com.domain.ports.farm.Algorithm;
import org.springframework.stereotype.Component;


@Component
public class StringToAlgorithm {

  public static Algorithm stringToAlgorithm(AlgorithmType algorithmType) {
    return switch (algorithmType) {
      case POWER_PRIORITY_ALGORITHM -> new PowerPriorityAlgorithm();
      case KNAPSACK_ALGORITHM -> new KnapsackAlgorithm();
      case POWER_HYSTERESIS_PRIORITY_ALGORITHM -> new PowerHysteresisPriorityAlgorithm();
    };
  }

}