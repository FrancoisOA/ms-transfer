package pe.com.bootcamp.microservice.transfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ewallet {
	private String id;
	private String idAccount;
	private String idCustomer;
	private Double balance;
}
