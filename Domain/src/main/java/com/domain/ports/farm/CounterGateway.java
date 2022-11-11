package com.domain.ports.farm;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CounterGateway {

  Double countAverage(List<Double> data);

  Double countDuration(Map<Date, Boolean> data);

  Double countSimpson(Map<Date, Double> data);
}
