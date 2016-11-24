package com.petclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableCaching
@EnableCircuitBreaker
@EnableEurekaClient
public class SpringbootJpaMavenApplication {

	public static void main(String[] args) {
		System.out.println("Port is "+args[0]);
		System.setProperty("server.port", args[0]);
		SpringApplication.run(SpringbootJpaMavenApplication.class, args);
	}
}
