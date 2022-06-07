
package pe.com.bootcamp.microservice.transfer.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import pe.com.bootcamp.microservice.transfer.entity.Transfer;

@Repository
public interface ITransferRepository extends ReactiveCrudRepository<Transfer, String>{
}
