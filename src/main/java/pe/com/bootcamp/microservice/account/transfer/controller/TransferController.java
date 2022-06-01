package pe.com.bootcamp.microservice.account.transfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.com.bootcamp.microservice.account.transfer.entity.Transfer;
import pe.com.bootcamp.microservice.account.transfer.service.impl.TransferServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/transfer")
public class TransferController {

	@Autowired
	private final TransferServiceImpl transferServiceImpl;

//	@PostMapping("/createTransfer")
//	@ResponseStatus(HttpStatus.CREATED)
//	public void createTrx(@RequestBody Transaction transferDTO) {
//		transferServiceImpl.save(transferDTO);
//	}
//	
//	@PostMapping("/createWithdraw")
//	@ResponseStatus(HttpStatus.CREATED)
//	public void createTrx(@RequestBody Transaction transferDTO) {
//		transferServiceImpl.createTrf(transferDTO);
//	}
//	
//	@PostMapping("/createTransfer")
//	@ResponseStatus(HttpStatus.CREATED)
//	public void createTransfer(@RequestBody Transaction transferDTO) {
//		transferServiceImpl.createTrf(transferDTO);
//	}

	@GetMapping(value = "/get/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseBody
	public Flux<Transfer> findAll() {
		return transferServiceImpl.findAllTrf();
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Mono<Transfer>> findTransferDTOById(@PathVariable("id") String id) {
		Mono<Transfer> transfer = transferServiceImpl.findByTrfId(id);
		return new ResponseEntity<Mono<Transfer>>(transfer, transfer != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Transfer> update(@PathVariable("id") String id, @RequestBody Transfer transferDTO) {
		return transferServiceImpl.updateTrf(id, transferDTO);
	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") String id) {
		transferServiceImpl.deleteTrf(id).subscribe();
	}
}