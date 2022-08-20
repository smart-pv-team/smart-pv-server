package consumption.persistence.device;

import consumption.ControlParameters;
import lombok.Data;
import management.device.Device;

@Data
public class ConsumerDeviceEntity extends Device {

  private Boolean isOn;
  private ControlParameters controlParameters;
}

