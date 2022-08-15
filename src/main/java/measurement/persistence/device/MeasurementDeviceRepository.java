package measurement.persistence.device;

import java.util.List;

public interface MeasurementDeviceRepository {
  List<MeasurementDeviceEntity> findAll();
  List<MeasurementDeviceEntity> findAllByFarmId(String farmId);

  MeasurementDeviceEntity getFirstById(String id);
}
