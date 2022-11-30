package com.domain.model.farm;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Device {

  private String id;
  private String farmId;
  private String name;
  private String ipAddress;
  private List<HttpEndpointData> endpoints;
  private Date creationDate;
  private DeviceModel deviceModel;
}
