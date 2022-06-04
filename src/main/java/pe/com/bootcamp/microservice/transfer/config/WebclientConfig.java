package pe.com.bootcamp.microservice.transfer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import pe.com.bootcamp.microservice.transfer.model.Account;
import pe.com.bootcamp.microservice.transfer.model.Ewallet;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebclientConfig {

	private final WebClient.Builder builder = WebClient.builder();

	public Mono<Account> getAccount(String idAccount) {
		log.info("Dentro del metodo getAccount");
		return builder.build().get().uri("http://localhost:8080/accounts/bank-account/" + idAccount).retrieve()
				.bodyToMono(Account.class);
	}

	public Mono<Account> updateAccount(Account Account) {
		log.info("Dentro del metodo updateAccount");
		return builder.build().put().uri("http://localhost:8080/accounts/bank-account/update")
				.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(Account)).retrieve()
				.bodyToMono(Account.class);
	}
	
	
	public Mono<Ewallet> getEwallet(String idAccount) {
		log.info("Dentro del metodo getEwallet");
		return builder.build().get().uri("http://localhost:8080/ewallet/" + idAccount).retrieve()
				.bodyToMono(Ewallet.class);
	}

	public Mono<Ewallet> updateEwallet(Ewallet Ewallet) {
		log.info("Dentro del metodo updateEwallet");
		return builder.build().put().uri("http://localhost:8080/ewallet/update")
				.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(Ewallet)).retrieve()
				.bodyToMono(Ewallet.class);
	}
	
	
}
