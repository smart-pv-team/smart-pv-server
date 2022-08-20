package consumption.persistence.device;

import consumption.ControlParameters;
import lombok.Data;
import management.device.Device;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
public class ConsumerDeviceEntity extends Device {

  private Boolean isOn;
  private ControlParameters controlParameters;
}

