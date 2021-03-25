package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(CarsRepository repository) {

		return args -> {
			// tag::new_constructor[]
			log.info("Loading " + repository.save(new Car("Reanult", "Clio", "110")));
			log.info("Loading " + repository.save(new Car("Peugeot", "208", "100")));
			// end::new_constructor[]
		};
	}
}