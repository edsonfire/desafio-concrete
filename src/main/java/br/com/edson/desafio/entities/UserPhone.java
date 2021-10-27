package br.com.edson.desafio.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.edson.desafio.entities.dto.UserPhoneDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPhone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private UUID id;
	private String ddd;
	private String number;
	
	
	public UserPhone(UserPhoneDTO phoneDTO) {
		this.id = phoneDTO.getId();
		this.ddd = phoneDTO.getDdd();
		this.number = phoneDTO.getNumber();
	}
	
	
	
	
	
}
