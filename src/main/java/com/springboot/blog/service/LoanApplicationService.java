package com.springboot.blog.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.blog.payload.LoanDto;

@Service
public interface LoanApplicationService {
	ResponseEntity<String> applyLoan(LoanDto loanDto);
}
