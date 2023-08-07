package com.springboot.blog.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.blog.payload.AccountDto;

@Service
public interface NewAccountCreationService {
	ResponseEntity<String> accountCreation(AccountDto accountDto);
}
