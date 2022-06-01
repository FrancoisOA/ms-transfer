package pe.com.bootcamp.microservice.account.transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ProjectOneMsTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectOneMsTransferApplication.class, args);
	}

}