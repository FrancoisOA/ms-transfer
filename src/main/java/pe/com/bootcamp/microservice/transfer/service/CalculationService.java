package pe.com.bootcamp.microservice.transfer.service;

@FunctionalInterface
public interface CalculationService {
	Double Calcule(Double amount, Double balance);
}
