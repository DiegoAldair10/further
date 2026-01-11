package com.further.spring.boot.further;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FurtherApplication {

	public static void main(String[] args) {
		SpringApplication.run(FurtherApplication.class, args);

	}

}
