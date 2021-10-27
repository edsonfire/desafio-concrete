package br.com.edson.desafio.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edson.desafio.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {

	
	
	Optional<User> findByEmail(String email);
	Optional<User> findByToken(String token);
	
	
	
	
}
