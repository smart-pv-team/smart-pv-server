package com.adapters.inbound.http.farm;

import com.adapters.inbound.http.Routing;
import com.adapters.outbound.http.devices.ResponseType;
import com.domain.model.farm.DeviceModel;
import com.domain.model.farm.Farm;
import com.domain.ports.farm.FarmRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FarmController {

  private final FarmRepository farmRepository;

  public FarmController(FarmRepository farmRepository) {
    this.farmRepository = farmRepository;
  }

  @GetMapping(Routing.Management.Farms.PATH)
  List<String> getFarms() {
    return farmRepository.findAll().stream().map(Farm::id).collect(Collectors.toList());
  }

  @GetMapping(Routing.Management.Farms.FarmId.PATH)
  ResponseEntity<Farm> getFarm(@PathVariable(Routing.FARM_ID_VARIABLE) String farmId) {
    return farmRepository
        .getById(farmId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(Routing.Management.Farms.FarmId.Parameters.Name.PATH)
  ResponseEntity<String> getFarmName(@PathVariable(Routing.FARM_ID_VARIABLE) String farmId) {
    return farmRepository
        .getById(farmId)
        .map(Farm::name)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(Routing.Management.DevicesModel.PATH)
  List<DeviceModel> getDevicesModel() {
    return List.of(DeviceModel.values());
  }


  @GetMapping(Routing.Management.ResponseOptions.PATH)
  List<ResponseType> getResponseOptions() {
    return List.of(ResponseType.values());
  }
}
