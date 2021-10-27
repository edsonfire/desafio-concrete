package br.com.edson.desafio.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.edson.desafio.entities.User;
import br.com.edson.desafio.entities.UserPhone;
import br.com.edson.desafio.entities.dto.UserDTO;
import br.com.edson.desafio.repository.UserPhoneRepository;
import br.com.edson.desafio.repository.UserRepository;
import br.com.edson.desafio.security.JwtTokenUtil;
import br.com.edson.desafio.util.UserDetailImpl;


@Service
public class UserService implements UserDetailsService {

	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserPhoneRepository phoneRepository;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public UserDTO create(UserDTO userDTO) {
		
		
		User user = new User(userDTO);
		
		
		
		
		for(UserPhone phone: user.getPhones()) 
		{
			
				phoneRepository.save(phone);
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		LocalDateTime date = LocalDateTime.now(); 
		user.setCreated(date);
		user.setModified(date);
		user.setLast_login(date);
		
		user.setToken(jwtTokenUtil.generateToken(user.getEmail()));
		
		return new UserDTO(userRepository.save(user));
	}
	
	
	
	public Optional<UserDTO> getOneByToken(String token) {
		
		Optional<User> user = userRepository.findByToken(token);
		Optional<UserDTO> userDTO = generateUserDTOOptional(user);
		
		
		
		
		return userDTO;
	}


	
	
	
	public Optional<UserDTO> getOneByID(UUID id) {
		
		Optional<User> user = userRepository.findById(id);
		
		Optional<UserDTO> userDTO = generateUserDTOOptional(user);
		
		return userDTO;
	}
	
	
	
	
public boolean  existEmail(String email) {
		
		Optional<User> user = userRepository.findByEmail(email);
		
		return user.isPresent();
	}
	


public Optional<UserDTO>  getByEmail(String email) {
	
	Optional<User> user = userRepository.findByEmail(email);
	Optional<UserDTO> userDTO = generateUserDTOOptional(user);
	
	return userDTO;
}




public Optional<User>  findByEmailtoAuthenticate(String email) {
	
	Optional<User> user = userRepository.findByEmail(email);
	
	
	return user;
}





public UserDTO registerLogin(String email) {
	Optional<User> user =  userRepository.findByEmail(email);
	UserDTO userDTO = new UserDTO();
	
	if(user.isPresent()) {
		User userToUpdate = user.get();
		
		userToUpdate.setLast_login(LocalDateTime.now());
		userToUpdate.setToken(jwtTokenUtil.generateToken(email));
		
		userRepository.save(userToUpdate);
	
		userDTO  = new UserDTO(userToUpdate);
		
		
	}
	
	return userDTO;
}

	
	
	
	public List<UserDTO> getAll(){
		
		
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		
		userDTOList =  userRepository.findAll().stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		
		
		return userDTOList;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByEmail(username);
		if(user.isEmpty()) {
		throw new UsernameNotFoundException("Usuário: ["+username+"] não encontrado");
		}
		
		return new UserDetailImpl(user);
		
	}
	
	
	





private Optional<UserDTO> generateUserDTOOptional(Optional<User> user) {
	Optional<UserDTO> userDTO;
	
	if(user.isPresent()) {
	
	
		userDTO = Optional.ofNullable(new UserDTO(user.get()));
	}else {
		userDTO = Optional.ofNullable(null);
	}
	return userDTO;
}

}