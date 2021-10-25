package br.com.edson.desafio.config.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.edson.desafio.config.repository.UserPhoneRepository;
import br.com.edson.desafio.config.repository.UserRepository;
import br.com.edson.desafio.entities.User;
import br.com.edson.desafio.entities.UserPhone;
import br.com.edson.desafio.util.UserDetailImpl;


@Service
public class UserService implements UserDetailsService {

	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserPhoneRepository phoneRepository;
	

	

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public User create(User user) {
		
		
		for(UserPhone phone: user.getPhones()) 
		{
			
				phoneRepository.save(phone);
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		LocalDateTime date = LocalDateTime.now(); 
		user.setCreated(date);
		user.setModified(date);
		user.setLast_login(date);
		
	///	user.setToken(tokenUtil.generateToken(new Userde user.getEmail())); setar o token
		
		return userRepository.save(user);
	}
	
	
	
	public Optional<User> getOneByToken(String token) {
		
		Optional<User> user = userRepository.findByToken(token);
		
		return user;
	}
	
	
	
	
	public Optional<User> getOneByID(UUID id) {
		
		Optional<User> user = userRepository.findById(id);
		
		return user;
	}
	
	
	
	
public boolean  existEmail(String email) {
		
		Optional<User> user = userRepository.findByEmail(email);
		
		return user.isPresent();
	}
	


public Optional<User>  getByEmail(String email) {
	
	Optional<User> user = userRepository.findByEmail(email);
	
	return user;
}

	
	
	
	public List<User> getAll(){
		
		return userRepository.findAll();
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByEmail(username);
		if(user.isEmpty()) {
		throw new UsernameNotFoundException("Usuário: ["+username+"] não encontrado");
		}
		
		return new UserDetailImpl(user);
		
	}
	
	
	
}
