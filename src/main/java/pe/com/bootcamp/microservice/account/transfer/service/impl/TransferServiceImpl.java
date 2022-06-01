package pe.com.bootcamp.microservice.account.transfer.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.com.bootcamp.microservice.account.transfer.config.WebclientConfig;
import pe.com.bootcamp.microservice.account.transfer.entity.Transfer;
import pe.com.bootcamp.microservice.account.transfer.model.Account;
import pe.com.bootcamp.microservice.account.transfer.repository.ITransferRepository;
import pe.com.bootcamp.microservice.account.transfer.service.CalculationService;
import pe.com.bootcamp.microservice.account.transfer.service.TransferService;
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
	public Mono<Transfer> saveTrx(Transfer trsf) {
		trsf.setStatus(true);
		trsf.setDateTransfer(new Date());				
		return trfRepo.save(trsf).doOnSuccess(x -> {
			log.info("Dentro de doOnSuccess");
			x.setStatus(true);
			CalculationService ca = (amount, balance) -> trsf.getTypeTransfer().equals("retiro") ? balance - amount
					: trsf.getTypeTransfer().equals("deposito") ? balance + amount : 0.0;
			webclient.getAccount(x.getIdAccount()).switchIfEmpty(Mono.empty()).flatMap(f -> {
				f.setBalance(ca.Calcule(x.getAmount(), f.getBalance()));
				trsf.setInitialBalance(f.getBalance());
				log.info("Dentro de subscribe");
				return webclient.updateAccount(f);
			});
		});
	}
   
	@Override
	public Mono<Transfer> saveTransfer(Transfer trsf) {
		trsf.setStatus(true);
		trsf.setDateTransfer(new Date());				
		return trfRepo.save(trsf).doOnSuccess(x -> {
			log.info("Dentro de doOnSuccess");
			x.setStatus(true);
			CalculationService ca = (amount, balance) -> trsf.getTypeTransfer().equals("retiro") ? balance - amount
					: trsf.getTypeTransfer().equals("deposito") ? balance + amount : 0.0;
			webclient.getAccount(x.getIdAccount()).switchIfEmpty(Mono.empty()).flatMap(f -> {
				f.setBalance(ca.Calcule(x.getAmount(), f.getBalance()));
				trsf.setInitialBalance(f.getBalance());
				log.info("Dentro de subscribe");
				return webclient.updateAccount(f);
			});
		});
	}
	
	 @Override
	    public Mono<Transfer> update(Transfer t) {
	        return  trfRepo.findById(t.getId())
	                .switchIfEmpty(Mono.empty())
	                .flatMap(o -> {
	                    if (t.getAmount() != null) {o.setAmount(t.getAmount());                         }
	                    if (t.getIdAccount() != null) {o.setIdAccount(t.getIdAccount());                   }
	                    if (t.getCurrency() != null) {o.setCurrency(t.getCurrency());                     }
	                    if (t.getDestinationAccount() != null) {o.setDestinationAccount(t.getDestinationAccount()); }
	                    if (t.getDestinationBank() != null) {o.setDestinationBank(t.getDestinationBank());       }
	                    if (t.getChannel() != null) {o.setChannel(t.getChannel());                       }
	                    if (t.getStatus() != null) {o.setStatus(t.getStatus());                         }
	                    return trfRepo.save(o);
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
