
package pe.com.bootcamp.microservice.transfer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import pe.com.bootcamp.microservice.transfer.entity.Transfer;

@Repository
public interface ITransferRepository extends ReactiveMongoRepository<Transfer, String>{
}
