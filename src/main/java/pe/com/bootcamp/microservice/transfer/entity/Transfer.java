package pe.com.bootcamp.microservice.transfer.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Document(collection = "tb_transfer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Transfer {
	@Id
	private String id;
	private String numDocUser;
	private String numOriginAccount; //id
	private String numDestinyAccount;//id
	private String bankDestiny;
	private String email;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date DateTrx;
	private String currency; 
	private Double totalAmount;
	private String viaDigital;
	private Boolean status;
}

 
