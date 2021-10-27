package br.com.edson.desafio.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.com.edson.desafio.entities.dto.UserDTO;
import br.com.edson.desafio.entities.dto.UserPhoneDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "UserDesafio")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String name;
	@Column(unique = true)
    private String email;
    private String password;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime last_login;
    private String token;
   
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_user_phone", 
				joinColumns = @JoinColumn(name="user_id"),
				inverseJoinColumns = @JoinColumn(name="phone_id")
			
			)
    @Builder.Default
	private Set<UserPhone> phones = new HashSet<>();
    
    
    
    	public User(UserDTO userDTO) {
		
		this.id = userDTO.getId();
		this.name = userDTO.getName();
		this.email = userDTO.getEmail();
		this.password = userDTO.getPassword();
		this.created = userDTO.getCreated();
		this.modified = userDTO.getModified();
		this.last_login = userDTO.getLast_login();
		this.token  = userDTO.getToken();
		this.phones = new HashSet<UserPhone>(userDTO.getPhones().stream().map(x -> new UserPhone(x)).collect(Collectors.toList()));
		
	}
    
    
    
}
	
