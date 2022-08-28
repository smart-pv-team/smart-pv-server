package server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import server.conf.BeforeStart;

@SpringBootApplication
@ComponentScan({"measurement", "management", "server", "consumption"})
@EntityScan({"measurement", "management", "server", "consumption"})
@EnableMongoRepositories({"measurement", "management", "server", "consumption"})
@EnableScheduling
@ConfigurationPropertiesScan({"measurement", "management", "server", "consumption"})
public class ServerApplication implements CommandLineRunner {

  private final BeforeStart beforeStart;

  public ServerApplication(BeforeStart beforeStart) {
    this.beforeStart = beforeStart;
  }

  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args);
  }

  @Override
  public void run(String[] args) {
    beforeStart.printLogs();
    //beforeStart.updateConsumingDeviceStatusInDatabase();
  }
}
