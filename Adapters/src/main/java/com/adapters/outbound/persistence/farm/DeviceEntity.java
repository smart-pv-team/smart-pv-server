package com.adapters.outbound.persistence.farm;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class DeviceEntity {

  @Id
  private String id;
  private String farmId;
  private String name;
  private String ipAddress;
  private List<HttpEndpointDataEntity> endpoints;
  private Date creationDate;
  private DeviceModelEntity deviceModel;
}
