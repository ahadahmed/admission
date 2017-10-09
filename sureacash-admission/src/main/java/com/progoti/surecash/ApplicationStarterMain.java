package com.progoti.surecash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.progoti.surecash")
public class ApplicationStarterMain {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStarterMain.class, args);

	}

}
