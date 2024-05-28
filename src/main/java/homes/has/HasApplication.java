package homes.has;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HasApplication {

	public static void main(String[] args) {
		SpringApplication.run(HasApplication.class, args);
	}

}
