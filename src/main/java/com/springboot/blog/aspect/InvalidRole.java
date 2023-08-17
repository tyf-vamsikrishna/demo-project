package com.springboot.blog.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.OK)
public class InvalidRole extends RuntimeException{

	public InvalidRole(String message) {
		super(message);
	}

}
