package com.domain.model.consumption;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consumption {

  private List<String> activeDevicesIds;
  private Integer activeDevicesNum;
  private Date date;
  private String farmId;

  private String id;

  public Consumption(List<String> activeDevicesIds, Integer activeDevicesNum, Date date, String farmId) {
    this.activeDevicesIds = activeDevicesIds;
    this.activeDevicesNum = activeDevicesNum;
    this.date = date;
    this.farmId = farmId;
  }

  public Consumption withDate(Date date) {
    return new Consumption(this.activeDevicesIds, this.activeDevicesNum, date, this.farmId, this.id);
  }

  public Consumption withActiveDevices(List<String> activeDevicesIds) {
    return new Consumption(activeDevicesIds, activeDevicesIds.size(), this.date, this.farmId, this.id);
  }
}
