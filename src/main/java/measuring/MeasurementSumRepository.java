package measuring;

import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasurementSumRepository extends MongoRepository<MeasurementSumEntity, String> {
  List<MeasurementSumEntity> findAllByIdAndDateBetween(String Id, Date from, Date to);
}
