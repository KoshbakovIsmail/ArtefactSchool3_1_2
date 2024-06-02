package ru.hogwarts.school32;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class School32Application {
	public static void main(String[] args) {
		SpringApplication.run(School32Application.class, args);
	}
}

