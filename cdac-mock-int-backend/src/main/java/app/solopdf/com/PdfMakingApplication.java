package app.solopdf.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("app.solopdf.com.entity")
@EnableJpaRepositories("app.solopdf.com.repository")
public class PdfMakingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfMakingApplication.class, args);
	}

}
