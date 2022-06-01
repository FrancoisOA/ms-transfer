package pe.com.bootcamp.microservice.account.transfer.service;

@FunctionalInterface
public interface CalculationService {
	Double Calcule(Double amount, Double balance);
}
