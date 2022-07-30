package measuring;

import java.util.Date;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class MeasurementSumEntity {
  @Id
  private String id;
  private final String farm;
  private final Float measurement;
  private final Date date;
}
