package pe.com.bootcamp.microservice.transfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String id;
    private String numberAccount; //NúmeroDeCuenta
    private String typeAccount; 
    private String idCustomer; //Id del cliente
    private String status;
    private Double balance; //Saldo actual disponible    
}
