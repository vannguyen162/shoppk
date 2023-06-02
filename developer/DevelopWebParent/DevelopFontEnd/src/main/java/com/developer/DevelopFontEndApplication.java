package com.developer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DevelopFontEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevelopFontEndApplication.class, args);
	}

}
