package pe.com.bootcamp.microservice.account.transfer.service;

import pe.com.bootcamp.microservice.account.transfer.model.Account;
import pe.com.bootcamp.microservice.account.transfer.entity.Transfer;
import reactor.core.publisher.Mono;

public interface TransferService extends CrudService<Transfer, String> {
	Mono<Account> getAccount(String idAccount);
}
