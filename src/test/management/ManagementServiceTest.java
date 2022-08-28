package management;

import consumption.ConsumptionService;
import consumption.persistence.device.ConsumptionDeviceRepository;
import measurement.MeasurementService;
import measurement.persistence.record.MeasurementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ManagementServiceTest {

  @Mock
  private ConsumptionDeviceRepository consumptionDeviceRepository;
  @Mock
  private MeasurementRepository measurementRepository;
  @Mock
  private MeasurementService measurementService;
  @Mock
  private ConsumptionService consumptionService;

  @Test
  void updateDevicesStatus() {

  }
}