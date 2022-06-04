package pe.com.bootcamp.microservice.transfer.service;

import pe.com.bootcamp.microservice.transfer.entity.Transfer;
import pe.com.bootcamp.microservice.transfer.model.Account;
import reactor.core.publisher.Mono;

public interface TransferService extends CrudService<Transfer, String> {
	Mono<Account> getAccount(String idAccount);
}
