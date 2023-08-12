package com.springboot.blog.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthenticationFailedException extends RuntimeException {
	public AuthenticationFailedException(String message) {
		super(message);
	}
}
