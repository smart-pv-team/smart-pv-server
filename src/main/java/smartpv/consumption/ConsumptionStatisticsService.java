package smartpv.consumption;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import smartpv.consumption.persistence.device.ConsumptionDeviceRepository;
import smartpv.consumption.persistence.record.ConsumptionRepository;

@Service
@AllArgsConstructor
public class ConsumptionStatisticsService {

  private final ConsumptionRepository consumptionRepository;
  private final ConsumptionDeviceRepository consumptionDeviceRepository;


}
