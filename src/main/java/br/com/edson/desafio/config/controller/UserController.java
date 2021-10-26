package br.com.edson.desafio.config.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edson.desafio.config.service.UserService;
import br.com.edson.desafio.entities.User;
import br.com.edson.desafio.util.Message;

@RestController
@RequestMapping("desafio/api/v1")
public class UserController {
	
	@Autowired
	private UserService userService;
	

	
	@GetMapping
	public ResponseEntity<List<User>> getAll(){
		
		List<User> userList = userService.getAll();
		
		return ResponseEntity.ok().body(userList);
	}

	
	
	
	@PostMapping("/users/cadastro")
	public ResponseEntity<Object> create(@RequestBody User user){
		
		
		if(userService.existEmail(user.getEmail())) {
		
			Message m = Message.builder()
						.mensagem("E-mail já existente")
						.build();
			
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(m);
		}else {
	
		User userbanco = userService.create(user);
		
			

	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userbanco);
		
		
		
		
		
		
		}
	}
	/*
	
	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getByID(@RequestParam String id){
		
		
		
		
		
		Optional<User> user = userService.getOneByID(UUID.fromString(id));
		
		if(user.isPresent()) {
			
			
			
			return ResponseEntity.ok().body(user);
		
		
		}else {
		
			Message m = Message.builder()
					.mensagem("email já cadastrado")
					.build();
		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(m);
		
		}
	}
*/
	
	
	
	
	
	
}
