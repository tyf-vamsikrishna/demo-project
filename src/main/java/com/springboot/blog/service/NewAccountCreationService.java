package com.springboot.blog.service;

import org.springframework.http.ResponseEntity;


import com.springboot.blog.payload.AccountDto;


public interface NewAccountCreationService {
	ResponseEntity<String> accountCreation(AccountDto accountDto);
}
