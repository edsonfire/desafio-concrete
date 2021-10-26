package br.com.edson.desafio.config;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {
private static final long serialVersionUID = -2550185165626007488L;
//public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

@Value("${jwt.token.secret}")
private String secret;

@Value("${jwt.token.expiration}")
private Long expiration;

//retorna o username do token jwt 
public String getUsernameFromToken(String token) {
return getClaimFromToken(token, Claims::getSubject);
}

//retorna expiration date do token jwt 
public Date getExpirationDateFromToken(String token) {
return getClaimFromToken(token, Claims::getExpiration);
}

public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	final Claims claims = getAllClaimsFromToken(token);
return claimsResolver.apply(claims);

}

//para retornar qualquer informa��o do token nos iremos precisar da secret key
private Claims getAllClaimsFromToken(String token) {
	return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
}

//check if the token has expired
private Boolean isTokenExpired(String token) {
	final Date expiration = getExpirationDateFromToken(token);
return expiration.before(new Date());
}

//gera token para user
public String generateToken(String email) {
	
return doGenerateToken(email);
}

//Cria o token e devine tempo de expira��o pra ele
private String doGenerateToken(String email) {
	  return Jwts.builder()
              .setSubject(email)
              .setExpiration(new Date(System.currentTimeMillis() + expiration))
              .signWith(SignatureAlgorithm.HS512, secret.getBytes())
              .compact();
}

//valida o token
public Boolean validateToken(String token, UserDetails userDetails) {
	final String username = getUsernameFromToken(token);
return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
}

}