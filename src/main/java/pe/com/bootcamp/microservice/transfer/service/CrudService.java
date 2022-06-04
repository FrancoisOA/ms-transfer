package pe.com.bootcamp.microservice.transfer.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudService <T, ID>{
    Flux<T> findAll();
    Mono<T> saveTransferAccounts(T t);
    Mono<T> saveTransferEWallet(T t);
    Mono<T> deleteById(ID id);
    Mono<T> findById(ID id);
}
