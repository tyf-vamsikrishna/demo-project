package com.springboot.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.AccountDto;
import com.springboot.blog.service.NewAccountCreationService;

@RestController
@RequestMapping("/api/auth")


public class AccountsController {

	
	@Autowired
	private NewAccountCreationService newAccountCreationService;
		
	@PostMapping("/newaccount")
	public ResponseEntity<String> accountCreation(AccountDto accountDto){
		return newAccountCreationService.accountCreation(accountDto);
	}
	}
	

