package com.domain.ports.measurement;

import com.domain.model.measurement.Measurement;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository {

  void save(Measurement measurement);

  List<Measurement> findAll();

  Optional<Measurement> findTopByDate();

  List<Measurement> findAllByFarmIdAndDateIsBetween(String farmId, Date from, Date to);

  List<Measurement> findAllByDeviceIdAndDateIsBetween(String deviceId, Date from, Date to);

  Optional<Measurement> findDeviceLastMeasurement(String deviceId);
}
