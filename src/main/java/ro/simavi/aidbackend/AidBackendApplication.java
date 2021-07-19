package ro.simavi.aidbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AidBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AidBackendApplication.class, args);
	}

}
