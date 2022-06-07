package pe.com.bootcamp.microservice.transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@EnableEurekaClient
@SpringBootApplication
public class ProjectOneMsTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectOneMsTransferApplication.class, args);
	}
    
	@LoadBalanced
	@Bean
	public WebClient.Builder getWebClient(){
		return  WebClient.builder();
	}
}