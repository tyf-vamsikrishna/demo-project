package com.springboot.blog.controller;


import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.AccountDto;
import com.springboot.blog.service.AccountOverViewService;
import com.springboot.blog.service.NewAccountCreationService;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AccountsController {

	
	@Autowired
	private NewAccountCreationService newAccountCreationService;
	
	@Autowired
	private AccountOverViewService accountOverViewService;
		
	
	//to create a new account for a user
	@PostMapping("/newaccount")
	public ResponseEntity<String> accountCreation(@NotNull @RequestBody AccountDto accountDto){
		return newAccountCreationService.accountCreation(accountDto);
	}
	
	
	//to get the over view of all the accounts a user has.
	@GetMapping("/accountoverview")
	public ResponseEntity<String> accountOverview(@RequestBody @NotNull String username){
		return accountOverViewService.accountOverview(username);
	}
	
	
}
	

