package com.adapters.outbound.persistence.management.algorithm.interval;

import com.domain.model.management.algorithm.Rule;
import com.domain.ports.management.farm.algorithm.RuleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RuleRepositoryImpl implements RuleRepository {

  private final RuleMongoRepository ruleMongoRepository;

  public void save(Rule rule) {
    ruleMongoRepository.save(RuleDocument.fromDomain(rule));
  }

  public List<Rule> getIntervalRules(String intervalId) {
    return ruleMongoRepository
        .getAllByIntervalId(intervalId)
        .stream()
        .map(RuleDocument::toDomain)
        .collect(Collectors.toList());
  }


  @Override
  public void delete(String ruleId) {
    ruleMongoRepository.deleteById(ruleId);
  }
}
