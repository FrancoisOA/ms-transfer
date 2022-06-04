package pe.com.bootcamp.microservice.transfer.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.com.bootcamp.microservice.transfer.config.WebclientConfig;
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
    
    @Override
    public Flux<Transfer> findAll() {
    	log.info("Dentro de findAll");
    	return  trfRepo.findAll();
    }

	@Override
	public Mono<Transfer> saveTransferAccounts(Transfer trsf) {
		trsf.setStatus(true);
		trsf.setDateTrx(new Date());				
		return trfRepo.save(trsf).doOnSuccess(x -> {
			log.info("Dentro de doOnSuccess");
			x.setStatus(true);
			CalculationService ca = (amount, balance) -> balance - amount;
			webclient.getAccount(x.getIdOriginAccount()).switchIfEmpty(Mono.empty()).flatMap(f -> {
				f.setBalance(ca.Calcule(x.getTotalAmount(), f.getBalance()));
				log.info("Dentro de subscribe");
				return webclient.updateAccount(f);
			});
		});
	}
     

	@Override
	public Mono<Transfer> saveTransferEWallet(Transfer trsf) {
		trsf.setStatus(true);
		trsf.setDateTrx(new Date());				
		return trfRepo.save(trsf).doOnSuccess(x -> {
			log.info("Dentro de doOnSuccess");
			x.setStatus(true);
			CalculationService ca = (amount, balance) -> balance - amount;
			webclient.getEwallet(x.getIdOriginAccount())
			.switchIfEmpty(Mono.empty())
			.flatMap(f -> {
				f.setBalance(ca.Calcule(x.getTotalAmount(), f.getBalance()));
				log.info("Dentro de subscribe");
				return webclient.updateEwallet(f);
			});
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
	        return webclient.getAccount(idAccount);
	    }
}
