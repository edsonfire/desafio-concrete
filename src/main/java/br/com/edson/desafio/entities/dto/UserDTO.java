package br.com.edson.desafio.entities.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.edson.desafio.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private UUID id;
	private String name;
	private String email;
    private String password;
    @JsonFormat(
    	      shape = JsonFormat.Shape.STRING,
    	      pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime created;
    @JsonFormat(
    	      shape = JsonFormat.Shape.STRING,
    	      pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime modified;
    @JsonFormat(
    	      shape = JsonFormat.Shape.STRING,
    	      pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime last_login;
    private String token;
   
    private Set<UserPhoneDTO> phones = new HashSet<>();

	public UserDTO(User user) {
		
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.created = user.getCreated();
		this.modified = user.getModified();
		this.last_login = user.getLast_login();
		this.token  = user.getToken();
		this.phones = new HashSet<UserPhoneDTO>(user.getPhones().stream().map(x -> new UserPhoneDTO(x)).collect(Collectors.toList()));
		
	}
    
    
    
	
	
}
