
package pe.com.bootcamp.microservice.account.transfer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import pe.com.bootcamp.microservice.account.transfer.entity.Transfer;
import reactor.core.publisher.Flux;

@Repository
public interface ITransferRepository extends ReactiveMongoRepository<Transfer, String>{
}
