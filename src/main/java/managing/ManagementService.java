package managing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagementService {
    private final ConsumerDeviceRepository consumerDeviceRepository;
    private int availableEnergy = -8;

    @Autowired
    public ManagementService(ConsumerDeviceRepository consumerDeviceRepository) {
        this.consumerDeviceRepository = consumerDeviceRepository;
    }

    public void setDeviceOn(String id, boolean newStatus) {
        //Exeception
        ConsumerDeviceEntity device = consumerDeviceRepository
                .findById(id)
                .orElseThrow();
        device.setOn(newStatus);
        consumerDeviceRepository.save(device);
    }

    public boolean isDeviceOn(String id) {
        //Exception
        return consumerDeviceRepository
                .findById(id)
                .orElseThrow()
                .isOn();
    }

    public void setDeviceParameters(String id, ConsumerDeviceParametersMapper consumerDeviceParametersMapper) {
        //Exception
        ConsumerDeviceEntity consumerDeviceEntity = consumerDeviceRepository.findById(id).orElseThrow();
        consumerDeviceRepository.save(consumerDeviceEntity.withParameters(consumerDeviceParametersMapper));
    }

    public ConsumerDeviceParametersMapper getDeviceParameters(String id) {
        //Exception
        ConsumerDeviceEntity consumerDeviceEntity = consumerDeviceRepository.findById(id).orElseThrow();
        return ConsumerDeviceParametersMapper.ofConsumerDevice(consumerDeviceEntity);
    }


    private String findHighestPriorityOffDevice() {
        return consumerDeviceRepository
                .findAll()
                .stream()
                .filter(d -> !d.isOn() && d.getPowerConsumption() <= availableEnergy)
                .min(Comparator.comparing(ConsumerDeviceEntity::getPriority))
                .map(ConsumerDeviceEntity::getId)
                .orElse(null);
    }

    private String findLowestPriorityOnDevice() {
        return consumerDeviceRepository
                .findAll()
                .stream()
                .filter(ConsumerDeviceEntity::isOn)
                .max(Comparator.comparing(ConsumerDeviceEntity::getPriority))
                .map(ConsumerDeviceEntity::getId)
                .orElse(null);
    }

    public String updateDevicesStatus() {
//        if (true) {
////            List<ConsumerDeviceParametersResponse> devicesParameters = getAllDevicesParameters();
////            devicesParameters.forEach(dp -> System.out.println(dp.toString()));
////            List<ConsumerDeviceStatusResponse> devicesStatuses = getAllDevicesStatuses();
////            devicesStatuses.forEach(s -> System.out.println(s.onStatus));
////            managingRequester.sendDeviceStatus("http://127.0.0.1:3010//test//post", true);
//            return "";
//        } else {
        List<ConsumerDeviceEntity> consumerDevices = consumerDeviceRepository.findAll();
        StringBuilder builder = new StringBuilder();
        String deviceId;
        int neededEnergy;
        int counter = 0;

        if (availableEnergy >= 0) {
            builder.append("Turned on devices: ");
            while (true) {
                deviceId = findHighestPriorityOffDevice();
                if (deviceId == null) {
                    return counter == 0 ? "No changes" : builder.toString();
                }
                neededEnergy = consumerDeviceRepository
                        .findById(deviceId)
                        .get()
                        .getPowerConsumption();
                availableEnergy -= neededEnergy;
                setDeviceOn(deviceId, true);
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
                setDeviceOn(deviceId, false);
                builder.append(deviceId + " ");
            }
            return builder.toString();
        }
    }

    private List<ConsumerDeviceParametersMapper> getAllDevicesParameters() {
        return consumerDeviceRepository
                .findAll()
                .stream()
                .map(ConsumerDeviceParametersMapper::ofConsumerDevice)
                .collect(Collectors.toList());
    }


    private List<ConsumerDeviceStatusMapper> getAllDevicesStatuses() {
        return consumerDeviceRepository
                .findAll()
                .stream()
                .map(ConsumerDeviceStatusMapper::ofConsumerDeviceEntity)
                .collect(Collectors.toList());
    }

}
