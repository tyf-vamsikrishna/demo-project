package com.springboot.blog.controller;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.AccountDto;
import com.springboot.blog.service.SelfTransferService;

@RestController
@RequestMapping("/api/transcation")
public class SelfTransferController {
	
	@Autowired
	private SelfTransferService selfTransferService;
	
	@PostMapping("/transfer")
	public ResponseEntity<String> selfTransfer(@NotNull @RequestBody AccountDto from, AccountDto to,long amount){
		return selfTransferService.selfTransfer(from, to,amount);
	}

}
