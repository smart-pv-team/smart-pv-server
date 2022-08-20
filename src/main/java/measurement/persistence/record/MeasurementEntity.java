package measurement.persistence.record;

import java.util.Date;
import java.util.Map;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document()
public class MeasurementEntity {

  private final String farmId;
  private final Float measurement;
  private final Map<String,Float> measurements;
  private final Date date;
  @Id
  private String id;
}
