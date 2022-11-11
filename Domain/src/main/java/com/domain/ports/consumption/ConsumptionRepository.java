package com.domain.ports.consumption;

import com.domain.model.consumption.Consumption;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionRepository {

  List<Consumption> findAll();

  void save(Consumption consumption);

  List<Consumption> findRecentEntities(String farmId, Date from, Date to);

  List<Consumption> findByDeviceIdAndDateBetween(String deviceId, Date from, Date to);

  Optional<Consumption> findLast(String farmId);
}
