package com.cytek2.cytek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://44.208.22.32")
public class CytekApplication {

	public static void main(String[] args) {
		SpringApplication.run(CytekApplication.class, args);
	}

}
