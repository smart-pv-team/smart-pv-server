package com.domain.ports.management.farm.algorithm;

import com.domain.model.management.algorithm.Rule;
import java.util.List;

public interface RuleRepository {

  void save(Rule rule);

  List<Rule> getIntervalRules(String intervalId);

  void delete(String ruleId);

}
