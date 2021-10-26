package br.com.edson.desafio.config.controller;

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

import br.com.edson.desafio.config.JwtTokenUtil;
import br.com.edson.desafio.config.JwtUserDetailsService;
import br.com.edson.desafio.config.service.UserService;
import br.com.edson.desafio.entities.User;
import br.com.edson.desafio.util.JwtRequest;
import br.com.edson.desafio.util.JwtResponse;
import br.com.edson.desafio.util.Message;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

@Autowired
private AuthenticationManager authenticationManager;

@Autowired
private JwtTokenUtil jwtTokenUtil;

@Autowired
private JwtUserDetailsService userDetailsService;
@Autowired
private UserService userService;

@RequestMapping(value = "/login", method = RequestMethod.POST)
public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
	
	//authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
	
	try {	
	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
	
	
	
	final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
	
	
	
	
	    Optional<User> user = userService.getByEmail(authenticationRequest.getEmail());
	    
	    
	    if(user.isPresent()) {
	    	
	    	
	    userService.registerLogin(user.get().getEmail());
	    	
	    	
	    	return ResponseEntity.ok(user.get());    	
	    }else {
	    	
	    	return ResponseEntity.ok(new User());
	    }
	
	
	
	
	
	}catch (BadCredentialsException e) {
		
		if(userService.existEmail(authenticationRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Message("Usuário e/ou senha inválidos"));
			
		}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("Usuário e/ou senha inválidos"));
		}
	}
}
/*
private void authenticate(String username, String password) throws Exception {
try {
	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
} catch (DisabledException e) {
	throw new Exception("USER_DISABLED", e);
} catch (BadCredentialsException e) {
	throw new Exception("INVALID_CREDENTIALS", e);
}
}*/
}