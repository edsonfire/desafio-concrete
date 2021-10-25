package br.com.edson.desafio.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.edson.desafio.entities.User;

public class UserDetailImpl implements UserDetails {

	
	private final Optional<User> user;
	
	public  UserDetailImpl(Optional<User> user) {
	 this.user = user;
	}
	
	
	
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// retornando lista vazia por não haver necessidade de implementar para o desafio
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		return user.orElse(new User()).getPassword();
		
	}

	@Override
	public String getUsername() {
		return user.orElse(new User()).getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// não implementado para fins do desafio
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// não implementado para fins do desafio
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// não implementado para fins do desafio
		return true;
	}

	@Override
	public boolean isEnabled() {
		// não implementado para fins do desafio
		return true;
	}
	
	
	public Optional<User> getUser() {
		return this.user;
	}
	
	
	

}
