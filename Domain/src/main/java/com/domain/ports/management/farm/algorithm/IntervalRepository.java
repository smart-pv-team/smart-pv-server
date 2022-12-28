package com.domain.ports.management.farm.algorithm;

import com.domain.model.management.algorithm.Interval;
import java.util.List;

public interface IntervalRepository {

  void save(Interval interval);

  List<Interval> getFarmIntervals(String farmId);

  void delete(String intervalId);
}
