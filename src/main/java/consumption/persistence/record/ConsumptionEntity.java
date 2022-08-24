package consumption.persistence.record;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
@AllArgsConstructor
public class ConsumptionEntity {

  private final List<String> activeDevicesIds;
  private final Integer activeDevicesNum;
  private final Date date;

  @Id
  private String id;

  public ConsumptionEntity(List<String> activeDevicesIds, Integer activeDevicesNum, Date date) {
    this.activeDevicesIds = activeDevicesIds;
    this.activeDevicesNum = activeDevicesNum;
    this.date = date;
  }
}
