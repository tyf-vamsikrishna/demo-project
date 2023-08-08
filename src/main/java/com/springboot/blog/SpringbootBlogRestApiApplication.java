package com.springboot.blog;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan(basePackages = "com.springboot.blog")
public class SpringbootBlogRestApiApplication {

	public static void main(String[] args) {
		
		
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
		System.out.println("Hello");
		
		
	}

	
}
