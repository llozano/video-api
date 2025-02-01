package com.proxy.videoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class VideoapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoapiApplication.class, args);
	}

}
