package server.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("system")
@ConstructorBinding
@Data
public class SystemProperties {

  private final Boolean measure;
  private final Boolean collectDevicesStatus;
  private final Boolean manageDevices;

  public SystemProperties(Boolean measure, Boolean collectDevicesStatus, Boolean manageDevices) {
    this.measure = measure;
    this.collectDevicesStatus = collectDevicesStatus;
    this.manageDevices = manageDevices;
  }
}

