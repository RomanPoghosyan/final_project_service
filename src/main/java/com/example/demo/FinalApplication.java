package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@ComponentScan("com.example.demo.controllers")
@ComponentScan("com.example.demo.configuration")
@ComponentScan("com.example.demo.services")
@EntityScan("com.example.demo.models")
@EnableJpaRepositories("com.example.demo.repos")
@EnableTransactionManagement
public class FinalApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalApplication.class, args);
	}

}
