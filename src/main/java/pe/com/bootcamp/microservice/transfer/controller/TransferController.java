package pe.com.bootcamp.microservice.transfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.com.bootcamp.microservice.transfer.entity.Transfer;
import pe.com.bootcamp.microservice.transfer.model.Account;
import pe.com.bootcamp.microservice.transfer.service.TransferService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path="/transfer")
public class TransferController {

	@Autowired
	TransferService transferService;

 	@GetMapping("/transfers")
 	@ResponseStatus(HttpStatus.OK)
	public Flux<Transfer> findAll() {
 		log.info("Metodo getfindAll");
		return transferService.findAll();
	}

    @PostMapping("/transfer-account")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Transfer> saveTrfAccounts(@RequestBody Transfer transfer){
    	log.info("Metodo saveTransfer");
        return transferService.saveTransferAccounts(transfer);
    }

    @PostMapping("/transfer-ewallet")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Transfer> saveTrfEWallet(@RequestBody Transfer transfer){
    	log.info("Metodo saveTransfer");
        return transferService.saveTransferEWallet(transfer);
    }
    
    @GetMapping("/transfer/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Account>getTransferByAccount(@PathVariable String id){
    	log.info("Metodo getDepositByAccount");
        return transferService.getAccount(id);
    }
}