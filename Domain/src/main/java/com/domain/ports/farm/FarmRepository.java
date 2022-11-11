package com.domain.ports.farm;

import com.domain.model.farm.Device;
import com.domain.model.farm.Farm;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmRepository {

  Optional<Farm> getById(String id);

  List<Farm> findAll();

  List<Device> getAllFarmDevices(String farmId);

  Optional<Device> getDeviceById(String farmId, String deviceId);

}
