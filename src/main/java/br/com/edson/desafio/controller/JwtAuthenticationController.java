package br.com.edson.desafio.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.edson.desafio.entities.User;
import br.com.edson.desafio.entities.dto.UserDTO;
import br.com.edson.desafio.security.JwtTokenUtil;
import br.com.edson.desafio.security.JwtUserDetailsService;
import br.com.edson.desafio.service.UserService;
import br.com.edson.desafio.util.JwtRequest;
import br.com.edson.desafio.util.JwtResponse;
import br.com.edson.desafio.util.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@Api(value = "login")
public class JwtAuthenticationController {

@Autowired
private AuthenticationManager authenticationManager;

@Autowired
private JwtTokenUtil jwtTokenUtil;

@Autowired
private JwtUserDetailsService userDetailsService;
@Autowired
private UserService userService;

@ApiOperation(value ="Efetua o login, e retorna um token para acesso aos endpoints")
@RequestMapping(value = "/login", method = RequestMethod.POST)
public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
	
	
	
	try {	
	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
	
	
	
	final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
	
	
	
	
	    Optional<UserDTO> userDTO = userService.getByEmail(authenticationRequest.getEmail());
	    
	    
	    if(userDTO.isPresent()) {
	    	
	    	
	        UserDTO userToReturn  =  userService.registerLogin(userDTO.get().getEmail());
	    	
	    	
	    	return ResponseEntity.ok(userToReturn);    	
	    }else {
	    	
	    	return ResponseEntity.ok(new UserDTO());
	    }
	
	
	
	
	
	}catch (BadCredentialsException e) {
		
		if(userService.existEmail(authenticationRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Message("Usuário e/ou senha inválidos"));
			
		}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("Usuário e/ou senha inválidos"));
		}
	}
}}