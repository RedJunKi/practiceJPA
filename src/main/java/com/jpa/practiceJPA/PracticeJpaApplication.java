package com.jpa.practiceJPA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport
public class PracticeJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeJpaApplication.class, args);
	}

}
