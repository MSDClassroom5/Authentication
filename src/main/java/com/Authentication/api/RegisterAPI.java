package com.Authentication.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.Authentication.jwthelper.JWTHelper;
import com.Authentication.jwthelper.JWTUtil;

import io.opentracing.Span;
import io.opentracing.Tracer;


@RestController
@RequestMapping("/register")
public class RegisterAPI {
	JWTUtil jwtUtil = new JWTHelper();

    @Autowired
    private Tracer tracer;	

	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> registerforCustomer(@RequestBody User u) {
		
		// add tracking code
    	Span span = tracer.buildSpan("registerforCustomer").start();
    	span.setTag("http.status_code",201);
		
		String username = u.getName();
		String password = u.getPassword();
		String email = u.getEmail();
		
		if (username != null && username.length() > 0 
				&& password != null && password.length() > 0 && email != null && email.length() > 0) {			

			// build the url with user API post method with user body (JSON)
			
			String apiHost = System.getenv("API_HOST");
			if ((apiHost == null) || (apiHost.isEmpty())) {
				apiHost = "localhost:8080";
			}
			
			String apiURL = "http://" + apiHost + "/api/customers";
			
			//String apiURL = "http://localhost:8080/api/customers";
			
		    RestTemplate restTemplate = new RestTemplate();
		    
		    //Add token and set up headers
		    Token registerToken = jwtUtil.createToken("registerToken");
		    HttpHeaders headers = new HttpHeaders();
		    headers.setBearerAuth(registerToken.getToken());
		    headers.setContentType(MediaType.APPLICATION_JSON);
			
		    HttpEntity<User> entity = new HttpEntity<>(u, headers);
		    
		    ResponseEntity<?> res = restTemplate.postForEntity(apiURL, entity, Boolean.class);
		    System.out.println("MSD Project group 5::Response Body: " + res.getBody());

		    span.finish();
		    
		    return res;
		}
		// bad request
		ResponseEntity<?> res = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		System.out.println("MSD Project group 5::Response Body: " + res.getBody());
	    span.finish();
		
		return res;
		
	}	
}
