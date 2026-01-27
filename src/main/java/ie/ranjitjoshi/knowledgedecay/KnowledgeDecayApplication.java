package ie.ranjitjoshi.knowledgedecay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KnowledgeDecayApplication {

	public static void main(String[] args) {
		SpringApplication.run(KnowledgeDecayApplication.class, args);
	}

}
