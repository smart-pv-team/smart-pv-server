package measurement.persistence.sum;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data

public class MeasurementSumEntity {

  private final String farmId;
  private final Float measurement;
  private final Date date;
  @Id
  private String id;
}
