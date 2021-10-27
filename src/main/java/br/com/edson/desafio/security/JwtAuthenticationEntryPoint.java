package br.com.edson.desafio.security;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
private static final long serialVersionUID = -7858869558953243875L;

@Override
public void commence(HttpServletRequest request, HttpServletResponse response,
AuthenticationException authException) throws IOException {
	
	

	 Map<String, Object> data = new HashMap<>();
     data.put("mensagem", "Nao Autorizado");
    

     OutputStream out = response.getOutputStream();
     ObjectMapper mapper = new ObjectMapper();
     mapper.writeValue(out, data);
     out.flush();
	

}

}