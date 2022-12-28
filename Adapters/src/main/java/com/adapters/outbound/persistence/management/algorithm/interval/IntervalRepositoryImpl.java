package com.adapters.outbound.persistence.management.algorithm.interval;

import com.domain.model.management.algorithm.Interval;
import com.domain.ports.management.farm.algorithm.IntervalRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class IntervalRepositoryImpl implements IntervalRepository {

  private final IntervalMongoRepository intervalMongoRepository;

  public void save(Interval interval) {
    intervalMongoRepository.save(IntervalDocument.fromDomain(interval));
  }

  public List<Interval> getFarmIntervals(String farmId) {
    return intervalMongoRepository
        .findAllByFarmId(farmId)
        .stream()
        .map(IntervalDocument::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void delete(String intervalId) {
    intervalMongoRepository.deleteById(intervalId);
  }
}
