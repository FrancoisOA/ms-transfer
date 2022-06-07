package pe.com.bootcamp.microservice.transfer.service.impl;


import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.com.bootcamp.microservice.transfer.config.WebclientConfig;
import pe.com.bootcamp.microservice.transfer.dto.AccountUpdateForTrxRequest;
import pe.com.bootcamp.microservice.transfer.dto.AccountUpdateForTrxResponse;
import pe.com.bootcamp.microservice.transfer.entity.Transfer;
import pe.com.bootcamp.microservice.transfer.model.Account;
import pe.com.bootcamp.microservice.transfer.repository.ITransferRepository;
import pe.com.bootcamp.microservice.transfer.service.CalculationService;
import pe.com.bootcamp.microservice.transfer.service.TransferService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    @Autowired
    ITransferRepository trfRepo;

    private WebclientConfig webclient= new WebclientConfig();
    
    @Autowired
    private  WebClient.Builder builder;
    
    public Function<AccountUpdateForTrxRequest, Mono<AccountUpdateForTrxResponse>> msAccountTrx = (objeto) -> builder
		      .baseUrl("http://ms-account")
		      .build().put()
			.uri("/Account/updateAccountTrx/")
			.body(Mono.just(objeto), AccountUpdateForTrxResponse.class)
			.retrieve()
			.bodyToMono(AccountUpdateForTrxResponse.class);
    
    @Override
    public Flux<Transfer> findAll() {
    	log.info("Dentro de findAll");
    	return  trfRepo.findAll();
    }

	@Override
	public Mono<Transfer> saveTransferAccounts(Transfer trsf) {
		trsf.setStatus(true);
		trsf.setDateTrx(new Date());
		AccountUpdateForTrxRequest accountUpdateForOrigin = AccountUpdateForTrxRequest.builder()
		.numAccount(trsf.getNumOriginAccount())
		.type(-1)
		.amount(trsf.getTotalAmount())
		.build();

        AccountUpdateForTrxRequest accountUpdateForDestiny = AccountUpdateForTrxRequest.builder()
		.numAccount(trsf.getNumDestinyAccount())
		.type(+1)
		.amount(trsf.getTotalAmount())
		.build();
		log.info("Antes de doOnSuccess");
		if(trsf != null) {
			log.info("+++++++++++++ entraaaa");
			return msAccountTrx.apply(accountUpdateForOrigin).flatMap(o -> {
				log.info("+++++++++++++ actuliza primer"+o.getNumAccount());
				  return msAccountTrx.apply(accountUpdateForDestiny).flatMap(r ->trfRepo.save(trsf)); 
			});
		}else{
			return null;
		}
		

	}
     

	@Override
	public Mono<Transfer> saveTransferEWallet(Transfer trsf) {
		trsf.setStatus(true);
		trsf.setDateTrx(new Date());				
		return trfRepo.save(trsf).doOnSuccess(x -> {
			log.info("Dentro de doOnSuccess");
			x.setStatus(true);
			CalculationService ca = (amount, balance) -> balance - amount;
			//webclient.getEwallet(x.getIdOriginAccount())
//			.switchIfEmpty(Mono.empty())
//			.flatMap(f -> {
//				f.setBalance(ca.Calcule(x.getTotalAmount(), f.getBalance()));
//				log.info("Dentro de subscribe");
//				return webclient.updateEwallet(f);
//			});
		});
	}
     
	
	
	    @Override
	    public Mono<Transfer> deleteById(String id) {
	        return  trfRepo.findById(id)
	                .switchIfEmpty(Mono.empty())
	                .flatMap(o -> {
	                    o.setStatus(false);
	                    return trfRepo.save(o);
	                });
	    }

	    @Override
	    public Mono<Transfer> findById(String id) {
	        return trfRepo.findById(id);
	    }

	    @Override
	    public Mono<Account> getAccount(String idAccount) {
	        //return webclient.getAccount(idAccount);
	        return null;
	    }
}
