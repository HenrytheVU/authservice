package com.example.authservice;

import com.example.authservice.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
