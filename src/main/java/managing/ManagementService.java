package managing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ManagementService {
    private final ConsumerDeviceRepository consumerDeviceRepository;
    private int availableEnergy = -8;

    @Autowired
    public ManagementService(ConsumerDeviceRepository consumerDeviceRepository) {
        this.consumerDeviceRepository = consumerDeviceRepository;
    }

    private void setDeviceOnStatus(String id, boolean newStatus) {
        ConsumerDeviceEntity device = consumerDeviceRepository
                .findById(id)
                .get();
        device.setOnStatus(newStatus);
        consumerDeviceRepository.save(device);
    }

    private boolean isDeviceOn(String id) {
        return consumerDeviceRepository
                .findById(id)
                .get()
                .getOnStatus();
    }

    private String findHighestPriorityOffDevice() {
        return consumerDeviceRepository
                .findAll()
                .stream()
                .filter(d -> !d.getOnStatus() && d.getPowerConsumption() <= availableEnergy)
                .min(Comparator.comparing(ConsumerDeviceEntity::getPriority))
                .map(ConsumerDeviceEntity::getId)
                .orElse(null);
    }

    private String findLowestPriorityOnDevice() {
        return consumerDeviceRepository
                .findAll()
                .stream()
                .filter(d -> d.getOnStatus())
                .max(Comparator.comparing(ConsumerDeviceEntity::getPriority))
                .map(ConsumerDeviceEntity::getId)
                .orElse(null);
    }

    public String updateDevicesStatus() {
        List<ConsumerDeviceEntity> consumerDevices = consumerDeviceRepository.findAll();
        StringBuilder builder = new StringBuilder();
        String deviceId;
        int neededEnergy;
        int counter = 0;

        if (availableEnergy >= 0) {
            builder.append("Turned on devices: ");
            while (true) {
                deviceId = findHighestPriorityOffDevice();
                if(deviceId == null) {
                    return counter == 0 ? "No changes" : builder.toString();
                }
                neededEnergy = consumerDeviceRepository
                        .findById(deviceId)
                        .get()
                        .getPowerConsumption();
                availableEnergy -= neededEnergy;
                setDeviceOnStatus(deviceId, true);
                builder.append(deviceId + " ");
                counter++;
            }
        } else {
            builder.append("Turned off devices: ");
            while (availableEnergy < 0) {
                deviceId = findLowestPriorityOnDevice();
                neededEnergy = consumerDeviceRepository
                        .findById(deviceId)
                        .get()
                        .getPowerConsumption();
                availableEnergy += neededEnergy;
                setDeviceOnStatus(deviceId, false);
                builder.append(deviceId + " ");
            }
            return builder.toString();
        }
    }
}
