package br.com.edson.desafio.security;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.edson.desafio.util.Message;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

@Autowired
private JwtUserDetailsService jwtUserDetailsService;

@Autowired
private JwtTokenUtil jwtTokenUtil;

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
throws ServletException, IOException {
	final String requestTokenHeader = request.getHeader("Authorization");

String username = null;
String jwtToken = null;


if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
	jwtToken = requestTokenHeader.substring(7);
try {
	username = jwtTokenUtil.getUsernameFromToken(jwtToken);
} catch (IllegalArgumentException e) {
	System.out.println("Unable to get JWT Token");
	response.setStatus(401);

} catch (ExpiredJwtException e) {
	System.out.println("JWT Token has expired");
	response.setStatus(401);

}
} else {
	logger.warn("JWT Token does not begin with Bearer String");
	response.setStatus(403);

}


if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	
	try {
				UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
				if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
	
	
	
	}catch (UsernameNotFoundException e) {
		response.setStatus(HttpStatus.NOT_FOUND.value());
	}



}
chain.doFilter(request, response);
}

}