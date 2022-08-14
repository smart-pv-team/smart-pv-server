package consumption;

import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

  private final ConsumerDeviceRepository consumerDeviceRepository;

  public ConsumerService(ConsumerDeviceRepository consumerDeviceRepository) {
    this.consumerDeviceRepository = consumerDeviceRepository;
  }

  public void turnDeviceOn(){

  }
}
