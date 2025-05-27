package api.webservices.inredd;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "api.webservices.inredd")
@EnableAsync
public class InreddApplication {
	public static void main(String[] args) {
		SpringApplication.run(InreddApplication.class, args);
	}

}
