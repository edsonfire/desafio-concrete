package br.com.edson.desafio.entities;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
	private Set<UserPhone> phones = new HashSet<>();
	
   /*
    
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", 
		joinColumns = @JoinColumn(name = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();
*/
}
