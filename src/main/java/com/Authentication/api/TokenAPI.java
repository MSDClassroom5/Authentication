package com.Authentication.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.Authentication.jwthelper.JWTHelper;
import com.Authentication.jwthelper.JWTUtil;

import io.opentracing.Span;
import io.opentracing.Tracer;


@RestController
public class TokenAPI {
	JWTUtil jwtUtil = new JWTHelper();

    @Autowired
    private Tracer tracer;

	@PostMapping("token")
	public ResponseEntity<?> createTokenforCustomer(@RequestBody User u) {
		
		// add tracking code
    	Span span = tracer.buildSpan("createTokenforCustomer").start();
    	span.setTag("http.status_code",201);
		
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
			if ((apiHost == null) || (apiHost.isEmpty())) {
				apiHost = "localhost:8080";
			}
			String apiURL = "http://" + apiHost + "/api/verifyuser";
			System.out.println("MSD Project group 5::apiHost: " + apiHost);
		    RestTemplate restTemplate = new RestTemplate();
		    
			ResponseEntity<Boolean> res = restTemplate.postForEntity(apiURL, u, Boolean.class);

	    	span.finish();
			
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
