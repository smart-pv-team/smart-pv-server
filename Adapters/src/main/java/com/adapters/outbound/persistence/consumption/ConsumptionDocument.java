package com.adapters.outbound.persistence.consumption;

import com.domain.model.consumption.Consumption;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@Document(collection = "consumptionEntity")
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionDocument {

  private List<String> activeDevicesIds;
  private Integer activeDevicesNum;
  private Date date;
  private String farmId;

  @Id
  private ObjectId id;

  @PersistenceCreator
  public ConsumptionDocument(List<String> activeDevicesIds, Integer activeDevicesNum, Date date, String farmId) {
    this.activeDevicesIds = activeDevicesIds;
    this.activeDevicesNum = activeDevicesNum;
    this.date = date;
    this.farmId = farmId;
  }

  public static ConsumptionDocument formDomain(Consumption consumption) {
    if (consumption == null) {
      return null;
    }
    ConsumptionDocumentBuilder builder = builder()
        .activeDevicesIds(consumption.getActiveDevicesIds())
        .activeDevicesNum(consumption.getActiveDevicesNum())
        .date(consumption.getDate())
        .farmId(consumption.getFarmId());
    if (consumption.getId() != null) {
      builder.id(new ObjectId(consumption.getId()));
    }
    return builder.build();
  }

  public static Consumption toDomain(ConsumptionDocument consumption) {
    if (consumption == null) {
      return null;
    }
    return Consumption.builder()
        .activeDevicesIds(consumption.getActiveDevicesIds())
        .activeDevicesNum(consumption.getActiveDevicesNum())
        .date(consumption.getDate())
        .farmId(consumption.getFarmId())
        .id(consumption.getId().toString())
        .build();
  }
}
