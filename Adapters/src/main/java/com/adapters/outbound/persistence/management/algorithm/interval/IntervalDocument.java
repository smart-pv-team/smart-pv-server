package com.adapters.outbound.persistence.management.algorithm.interval;

import com.domain.model.management.algorithm.Interval;
import lombok.Builder;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "intervalEntity")
public record IntervalDocument(@Id String id, Float lowerBound, Float upperBound, String farmId) {

  public static Interval toDomain(IntervalDocument intervalDocument) {
    return new Interval(intervalDocument.id, intervalDocument.lowerBound, intervalDocument.upperBound,
        intervalDocument.farmId);
  }

  public static IntervalDocument fromDomain(Interval domain) {
    return new IntervalDocument(domain.getId(), domain.getLowerBound(), domain.getUpperBound(), domain.getFarmId());
  }


}
