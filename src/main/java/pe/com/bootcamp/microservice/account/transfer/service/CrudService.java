package pe.com.bootcamp.microservice.account.transfer.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudService <T, ID>{
    Flux<T> findAll();
    Mono<T> saveTrx(T t);
    Mono<T> saveTransfer(T t);
    Mono<T> update(T t);
    Mono<T> deleteById(ID id);
    Mono<T> findById(ID id);
}
