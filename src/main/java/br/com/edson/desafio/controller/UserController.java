package br.com.edson.desafio.controller;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.edson.desafio.entities.User;
import br.com.edson.desafio.entities.dto.UserDTO;
import br.com.edson.desafio.security.JwtTokenUtil;
import br.com.edson.desafio.service.UserService;
import br.com.edson.desafio.util.Message;

@RestController
@RequestMapping("desafio/api/v1")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	

	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAll(){
		
		List<UserDTO> userList = userService.getAll();
		
		return ResponseEntity.ok().body(userList);
	}

	
	
	
	@PostMapping("/users/cadastro")
	public ResponseEntity<Object> create(@RequestBody UserDTO userDTO){
		
		
		if(userService.existEmail(userDTO.getEmail())) {
		
			Message m = Message.builder()
						.mensagem("E-mail já existente")
						.build();
			
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(m);
		}else {
	
		UserDTO userbanco = userService.create(userDTO);
		
			

	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userbanco);
		
		
		
		
		
		
		}
	}
	
	
	
	
	
	
	@GetMapping("/users/perfil/{id}")
	public ResponseEntity<Object> getByID(@PathVariable String id, HttpServletRequest request){
		
		
		
		
		
		Optional<UserDTO> user = userService.getOneByID(UUID.fromString(id));
		
		if(user.isPresent()) {
			String tokenFromRequest =jwtTokenUtil.getTokenFromRequest(request);	
			
			
			if(user.get().getToken().equals(tokenFromRequest)) {
				
				
				if(LocalDateTime.now().isAfter(user.get().getLast_login().plusMinutes(30))){
			
					Message m = Message.builder()
							.mensagem("Sessão Expirada")
							.build();
				
				
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(m);
				
					
					
					
				}else {
					
					return ResponseEntity.ok().body(user);	
				}
				
				
				
				
				
					
			}else {
				
				Message m = Message.builder()
						.mensagem("Não Autorizado")
						.build();
			
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(m);
			
				
			}
			
			
			
			
		
		
		}else {
		
			Message m = Message.builder()
					.mensagem("Usuário não encontrado")
					.build();
		
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(m);
		
		}
	}

	
	
	
	
	
	
}
