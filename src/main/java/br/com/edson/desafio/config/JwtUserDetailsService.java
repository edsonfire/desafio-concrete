package br.com.edson.desafio.config;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.edson.desafio.config.service.UserService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
    private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<br.com.edson.desafio.entities.User> user = userService.getByEmail(email);
		
		if(user.isPresent()) {
			
			if (user.get().getEmail().equals(email)) {
				return new User(email, user.get().getPassword(),
						new ArrayList<>());
			} else {
				throw new UsernameNotFoundException("User not found with email: " + email);
			}
			
		}else {
			
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
		
		
		
		
		
		
		
	
	}
}