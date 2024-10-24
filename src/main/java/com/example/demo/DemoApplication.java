package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@PropertySource("file:./secrets.properties")
public class DemoApplication {
  @Autowired
    private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	 @Bean
    public void printSecrets() {
        System.out.println("Google Client Secret: " + env.getProperty("GOOGLE_CLIENT_SECRET"));
    }

}
