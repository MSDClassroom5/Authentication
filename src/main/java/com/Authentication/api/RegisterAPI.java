package com.Authentication.api;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.Authentication.jwthelper.JWTHelper;
import com.Authentication.jwthelper.JWTUtil;


@RestController
@RequestMapping("/register")
public class RegisterAPI {
	JWTUtil jwtUtil = new JWTHelper();
	

	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> registerforCustomer(@RequestBody User u) {
		
		String username = u.getName();
		String password = u.getPassword();
		String email = u.getEmail();
		
		if (username != null && username.length() > 0 
				&& password != null && password.length() > 0 && email != null && email.length() > 0) {			

			// build the url with user API post method with user body (JSON)
			String url = "http://localhost:8080/api/customers";
			
		    RestTemplate restTemplate = new RestTemplate();
		    
			ResponseEntity<Boolean> res = restTemplate.postForEntity(url, u, Boolean.class);
			
			return res;	
		}
		// bad request
		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		
	}	
}
