package server;

import measuring.MeasurementEntity;
import measuring.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Date;

@SpringBootApplication
@ComponentScan("measuring")
@EntityScan("measuring")
@EnableMongoRepositories("measuring")
public class ServerApplication implements CommandLineRunner {

	@Autowired
	private MeasurementRepository measurementRepository;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	public void run(String[] args) throws Exception {
		measurementRepository.save(new MeasurementEntity("1",1F,new Date()));
		System.out.println(measurementRepository.getAllByDeviceId("1"));
	}

}
