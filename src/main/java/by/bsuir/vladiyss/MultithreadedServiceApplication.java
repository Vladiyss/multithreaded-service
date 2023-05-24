package by.bsuir.vladiyss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MultithreadedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultithreadedServiceApplication.class, args);
	}

}
