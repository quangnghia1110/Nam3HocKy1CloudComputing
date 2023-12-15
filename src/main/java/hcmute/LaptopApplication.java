package hcmute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hcmute.utils.DatabaseInitalizer;

@SpringBootApplication
public class LaptopApplication {

	@Autowired
	private DatabaseInitalizer dbInitializer;

	public static void main(String[] args) {
		SpringApplication.run(LaptopApplication.class, args);
	}

	@Bean
	public CommandLineRunner myCommandLineRunner() {
		return args -> {
			dbInitializer.initDb();
		};
	}
}
