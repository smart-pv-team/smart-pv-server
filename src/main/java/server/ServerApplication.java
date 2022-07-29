package server;

import managing.ConsumerDeviceRepository;
import measuring.MeasurementRepository;
import measuring.MeasuringDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"measuring", "managing", "server"})
@EntityScan({"measuring", "managing","server"})
@EnableMongoRepositories({"measuring", "managing","server"})
@EnableScheduling
@ConfigurationPropertiesScan({"measuring", "managing","server"})
public class ServerApplication implements CommandLineRunner {

  @Autowired
  private MeasurementRepository measurementRepository;
  @Autowired
  private MeasuringDeviceRepository measuringDeviceRepository;

  @Autowired
  private ConsumerDeviceRepository consumerDeviceRepository;

  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }

  @Override
  public void run(String[] args) {
    System.out.println(measurementRepository.findAll());
    System.out.println(measuringDeviceRepository.findAll());
    System.out.println(consumerDeviceRepository.findAll());
  }

}
