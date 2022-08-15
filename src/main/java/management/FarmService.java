package management;

import management.persistence.FarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FarmService {

  private final ManagementService managementService;
  private final FarmRepository farmRepository;

  @Autowired
  public FarmService(ManagementService managementService, FarmRepository farmRepository) {
    this.managementService = managementService;
    this.farmRepository = farmRepository;
  }

  public String updateAllFarmDeviceStatus() {
    return String.join("\n",
        farmRepository.findAll().stream().map(managementService::updateDevicesStatus).toList());
  }
}