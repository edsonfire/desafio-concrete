package br.com.edson.desafio.entities.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.edson.desafio.entities.User;
import br.com.edson.desafio.entities.UserPhone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPhoneDTO {

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private UUID id;
	private String ddd;
	private String number;
	
	
	
	public UserPhoneDTO(UserPhone userPhone) {

		this.id = userPhone.getId();
		this.ddd = userPhone.getDdd();
		this.number = userPhone.getNumber();
	}
	
	
	
	
	
}
