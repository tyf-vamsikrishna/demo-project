package com.springboot.blog.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.blog.payload.AccountDto;

@Service
public interface SelfTransferService {
	ResponseEntity<String> selfTransfer(AccountDto from, AccountDto to, long amount);
}
