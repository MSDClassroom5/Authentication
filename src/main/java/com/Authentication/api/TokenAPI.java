package com.Authentication.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.Authentication.jwthelper.JWTHelper;
import com.Authentication.jwthelper.JWTUtil;


@RestController
public class TokenAPI {
	JWTUtil jwtUtil = new JWTHelper();
	

	@PostMapping("token")
	public ResponseEntity<?> createTokenforCustomer(@RequestBody User u) {
		
		String username = u.getName();
		String password = u.getPassword();
		
		if (username != null && username.length() > 0 
				&& password != null && password.length() > 0 ) {
			
//			JSONObject userJsonObject = new JSONObject();
//			userJsonObject.put("username", username);
//			userJsonObject.put("password", password);

			// build the url with user API post method with user body (JSON)
			//String url = "http://localhost:8080/api/verifyuser";

			String apiHost = System.getenv("API_HOST");
			String apiURL = "http://" + apiHost + "/api/verifyuser";
			System.out.println("MSD Project group 5::apiHost: " + apiHost);
		    RestTemplate restTemplate = new RestTemplate();
		    
			ResponseEntity<Boolean> res = restTemplate.postForEntity(apiURL, u, Boolean.class);
			
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
	
}
