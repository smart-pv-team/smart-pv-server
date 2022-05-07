package measuring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final MeasuringDeviceRepository measuringDeviceRepository;
    private final MeasuringRequester measuringRequester;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, MeasuringDeviceRepository measuringDeviceRepository, MeasuringRequester measuringRequester) {
        this.measurementRepository = measurementRepository;
        this.measuringDeviceRepository = measuringDeviceRepository;
        this.measuringRequester = measuringRequester;
    }

    public List<MeasurementEntity> makeMeasurements() {
        List<MeasurementEntity> measurements = getMeasurements();
        saveMeasurements(measurements);
        return measurements;
    }

    private void saveMeasurements(List<MeasurementEntity> measurementEntities) {
        measurementRepository.saveAll(measurementEntities);
    }

    private List<MeasurementEntity> getMeasurements() {
        return measuringDeviceRepository
                .findAll()
                .stream()
                .map(this::getMeasurement)
                .collect(Collectors.toList());
    }

    private MeasurementEntity getMeasurement(MeasuringDeviceEntity measuringDeviceEntity) {
        return measuringRequester.getMeasurement(measuringDeviceEntity.getIpAddress().concat(measuringDeviceEntity.getDataEndpoint())).toMeasurementEntity();
    }
}
