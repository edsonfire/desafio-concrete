package br.com.edson.desafio.util;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ValidaJson {

	public boolean JsonValidate(String jsonInString) {
		
		boolean result = false; 
		
	    try {
	      if( new ObjectMapper().readTree(jsonInString) != null) {
	    	  result = true;
	      }
	    } catch (IOException e) {
	       result = false;
	    }
		
	    return result;
	}
	
	
	
}
