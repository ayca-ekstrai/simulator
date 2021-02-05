package io.ekstrai.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.DriverManager;

@SpringBootApplication
@EnableScheduling
public class SimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulatorApplication.class, args);
	}


}
//@EnableConfigurationProper ties(ConfigProperties.class)