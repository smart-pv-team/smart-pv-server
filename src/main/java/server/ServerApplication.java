package server;

import consumption.ConsumerDeviceRepository;
import measurement.MeasurementRepository;
import measurement.MeasurementDeviceRepository;
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
@ComponentScan({"measurement", "managment", "server", "consumption"})
@EntityScan({"measurement", "managment", "server", "consumption"})
@EnableMongoRepositories({"measurement", "managment", "server", "consumption"})
@EnableScheduling
@ConfigurationPropertiesScan({"measurement", "managment", "server", "consumption"})
public class ServerApplication implements CommandLineRunner {

  @Autowired
  private MeasurementRepository measurementRepository;
  @Autowired
  private MeasurementDeviceRepository measurementDeviceRepository;

  @Autowired
  private ConsumerDeviceRepository ConsumerDeviceRepository;

  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }

  @Override
  public void run(String[] args) {
    System.out.println(measurementRepository.findAll());
    System.out.println(measurementDeviceRepository.findAll());
    System.out.println(ConsumerDeviceRepository.findAll());
  }

}
