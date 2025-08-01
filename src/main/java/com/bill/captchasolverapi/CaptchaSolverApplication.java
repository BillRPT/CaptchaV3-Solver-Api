package com.bill.captchasolverapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CaptchaSolverApplication {

	public static void startServer(String[] args) {
		SpringApplication.run(CaptchaSolverApplication.class, args);
		System.out.println("http://localhost:8080/SolveV3?anchor=<URL>");
	}

}
