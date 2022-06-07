package pe.com.bootcamp.microservice.transfer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import pe.com.bootcamp.microservice.transfer.dto.AccountUpdateForTrxRequest;
import pe.com.bootcamp.microservice.transfer.dto.AccountUpdateForTrxResponse;
import pe.com.bootcamp.microservice.transfer.model.Account;
import pe.com.bootcamp.microservice.transfer.model.Ewallet;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebclientConfig {


	@Autowired
    private  WebClient.Builder builder;

	public Mono<AccountUpdateForTrxResponse> getAccount(AccountUpdateForTrxRequest numAccount) {
		log.info("Dentro del metodo getAccount");
		return builder
				.baseUrl("http://ms-account")
			    .build()
			    .post()
				.uri("/Account/numAccount/")
				.body(Mono.just(numAccount), AccountUpdateForTrxResponse.class)
				.retrieve()
				.bodyToMono(AccountUpdateForTrxResponse.class);
	}

	public Mono<AccountUpdateForTrxResponse> updateAccount(AccountUpdateForTrxRequest Account) {
		log.info("Dentro del metodo updateAccount");
		return builder
				.baseUrl("http://ms-account")
			    .build()
			    .put()
				.uri("/Account/updateAccountTrx/")
				.body(Mono.just(Account), AccountUpdateForTrxResponse.class)
				.retrieve()
				.bodyToMono(AccountUpdateForTrxResponse.class);
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
