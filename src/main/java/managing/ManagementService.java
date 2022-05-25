package managing;

import measuring.MeasurementEntity;
import measuring.MeasurementRepository;
import measuring.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagementService {
    private final ConsumerDeviceRepository consumerDeviceRepository;
    private final MeasurementRepository measurementRepository;
    private final MeasurementService measurementService;
    private float availableEnergy;

    @Autowired
    public ManagementService(ConsumerDeviceRepository consumerDeviceRepository, MeasurementRepository measurementRepository, MeasurementService measurementService) {
        this.consumerDeviceRepository = consumerDeviceRepository;
        this.measurementRepository = measurementRepository;
        this.measurementService = measurementService;
    }

    public void setDeviceOn(String id, boolean newStatus) {
        //Exeception
        System.out.println(id + " " + newStatus);
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
        consumerDeviceRepository.save(consumerDeviceEntity.withParameters(consumerDeviceParametersMapper).withId(id));
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
        updateAvailableEnergy();
        System.out.println("[AVAILABLE ENERGY] " + this.availableEnergy);

        List<ConsumerDeviceEntity> consumerDevices = consumerDeviceRepository.findAll();
        StringBuilder builder = new StringBuilder();
        String deviceId;
        float neededEnergy;
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

    public List<String> getAllDevicesIds() {
        return consumerDeviceRepository
                .findAll()
                .stream()
                .map(ConsumerDeviceEntity::getId)
                .collect(Collectors.toList());
    }

    public void updateAvailableEnergy() {
        float producedEnergy = measurementService.getLatestTotalMeasurements();
        float usedEnergy = consumerDeviceRepository
                .findAll()
                .stream()
                .filter(ConsumerDeviceEntity::isOn)
                .map(ConsumerDeviceEntity::getPowerConsumption)
                .reduce((float) 0, Float::sum);
        availableEnergy = producedEnergy - usedEnergy;
    }
}
