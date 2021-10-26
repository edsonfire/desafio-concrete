package br.com.edson.desafio.config.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.edson.desafio.config.JwtTokenUtil;
import br.com.edson.desafio.config.service.UserService;
import br.com.edson.desafio.entities.User;
import br.com.edson.desafio.util.Message;

@RestController
@RequestMapping("desafio/api/v1")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	

	
	@GetMapping
	public ResponseEntity<List<User>> getAll(){
		
		List<User> userList = userService.getAll();
		
		return ResponseEntity.ok().body(userList);
	}

	
	
	
	@PostMapping("/users/cadastro")
	public ResponseEntity<Object> create(@RequestBody User user){
		
		
		if(userService.existEmail(user.getEmail())) {
		
			Message m = Message.builder()
						.mensagem("E-mail j� existente")
						.build();
			
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(m);
		}else {
	
		User userbanco = userService.create(user);
		
			

	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userbanco);
		
		
		
		
		
		
		}
	}
	
	
	
	
	
	
	@GetMapping("/users/perfil/{id}")
	public ResponseEntity<Object> getByID(@PathVariable String id, HttpServletRequest request){
		
		
		
		
		
		Optional<User> user = userService.getOneByID(UUID.fromString(id));
		
		if(user.isPresent()) {
			String tokenFromRequest =jwtTokenUtil.getTokenFromRequest(request);	
			
			
			if(user.get().getToken().equals(tokenFromRequest)) {
				
				
				if(LocalDateTime.now().isAfter(user.get().getLast_login().plusMinutes(30))){
			
					Message m = Message.builder()
							.mensagem("Sess�o Expirada")
							.build();
				
				
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(m);
				
					
					
					
				}else {
					
					return ResponseEntity.ok().body(user);	
				}
				
				
				
				
				
					
			}else {
				
				Message m = Message.builder()
						.mensagem("N�o Autorizado")
						.build();
			
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(m);
			
				
			}
			
			
			
			
		
		
		}else {
		
			Message m = Message.builder()
					.mensagem("Usu�rio n�o encontrado")
					.build();
		
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(m);
		
		}
	}

	
	
	
	
	
	
}
