package com.Authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthenticationApplication {

	public static void main(String[] args) {
		System.out.println("MSD Project group 5::Starting Auth application");
		SpringApplication.run(AuthenticationApplication.class, args);
	}

}
