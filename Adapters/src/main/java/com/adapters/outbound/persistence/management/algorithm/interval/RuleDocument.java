package com.adapters.outbound.persistence.management.algorithm.interval;

import com.domain.model.management.algorithm.Rule;
import lombok.Builder;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Document(collection = "ruleEntity")
public record RuleDocument(@Id String id, String intervalId, String deviceId, String action) {

  public static Rule toDomain(RuleDocument ruleDocument) {
    return new Rule(ruleDocument.id, ruleDocument.intervalId, ruleDocument.deviceId, ruleDocument.action);
  }

  public static RuleDocument fromDomain(Rule domain) {
    return new RuleDocument(domain.getId(), domain.getIntervalId(), domain.getDeviceId(), domain.getAction());
  }
}
