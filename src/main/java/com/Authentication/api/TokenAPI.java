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
@RequestMapping("/token")
public class TokenAPI {
	JWTUtil jwtUtil = new JWTHelper();
	

	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> createTokenforCustomer(@RequestBody User u) {
		
		String username = u.getUsername();
		String password = u.getPassword();
		
		if (username != null && username.length() > 0 
				&& password != null && password.length() > 0 ) {
			
//			JSONObject userJsonObject = new JSONObject();
//			userJsonObject.put("username", username);
//			userJsonObject.put("password", password);

			// build the url with user API post method with user body (JSON)
			String url = "http://localhost:8080/api/verifyuser";
			
		    RestTemplate restTemplate = new RestTemplate();
		    
			ResponseEntity<Boolean> res = restTemplate.postForEntity(url, u, Boolean.class);
			
			// if user name and password matched the customer DB then return the token
			if (res.getBody()) {
				Token token = jwtUtil.createToken(username);
				ResponseEntity<?> response = ResponseEntity.ok(token);
				return response;	
			}
		}
		// bad request
		return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		
	}	
	
//	@RequestMapping()
//	@PostMapping("/register")
//	public ResponseEntity<?> createTokenforCustomer(@RequestBody User u) {
//		
//	}	
}
