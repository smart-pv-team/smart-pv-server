package consumption.persistence.status;

import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
public class ConsumerDeviceStatusEntity {

  private final List<String> activeDevicesIds;
  private final Integer activeDevicesNum;
  private final Date date;

  @Id
  private String id;
}
