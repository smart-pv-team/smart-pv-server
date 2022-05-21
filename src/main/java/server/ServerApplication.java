package server;

import managing.ConsumerDeviceActivityScheduler;
import managing.ConsumerDeviceEntity;
import managing.ConsumerDeviceRepository;
import managing.ManagementService;
import measuring.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

import static java.lang.Thread.sleep;

@SpringBootApplication
@ComponentScan({"measuring", "managing"})
@EntityScan({"measuring", "managing"})
@EnableMongoRepositories({"measuring", "managing"})
@EnableScheduling
public class ServerApplication implements CommandLineRunner {

    @Autowired
    private MeasurementRepository measurementRepository;
    @Autowired
    private MeasuringDeviceRepository measuringDeviceRepository;
    @Autowired
    private MeasurementService measurementService;
    @Autowired
    private MeasurementScheduler measurementScheduler;
    @Autowired
    private ConsumerDeviceRepository consumerDeviceRepository;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private ConsumerDeviceActivityScheduler consumerDeviceActivityScheduler;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {
//      measurementRepository.save(new MeasurementEntity("1", 1F, new Date()));
        measuringDeviceRepository.deleteAll();
        measurementRepository.deleteAll();
        consumerDeviceRepository.deleteAll();
        measuringDeviceRepository.saveAll(
                List.of(
                        new MeasuringDeviceEntity("http://127.0.0.1:3005", "/measurement/device/1"),
                        new MeasuringDeviceEntity("http://127.0.0.1:3005", "/measurement/device/2"),
                        new MeasuringDeviceEntity("http://127.0.0.1:3005", "/measurement/device/3")
                )
        );
        consumerDeviceRepository.saveAll(
                List.of(
                        new ConsumerDeviceEntity("http://127.0.0.1:3010", "/consumer/device/1", 0, true, 6, 2, 3),
                        new ConsumerDeviceEntity("http://127.0.0.1:3010", "/consumer/device/2", 0, false, 5, 1, 3),
                        new ConsumerDeviceEntity("http://127.0.0.1:3010", "/consumer/device/3", 1, true, 7, 2, 3)
                )
        );

        System.out.println(measurementRepository.findAll());
        System.out.println(measuringDeviceRepository.findAll());
        sleep(1000*60*2);
        System.out.println(measurementRepository.findAll());
        System.out.println(measuringDeviceRepository.findAll());
    }

}
