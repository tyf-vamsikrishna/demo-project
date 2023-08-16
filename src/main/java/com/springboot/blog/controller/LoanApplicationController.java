package com.springboot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.LoanDto;
import com.springboot.blog.service.LoanApplicationService;

@RestController
@RequestMapping("/api/loan")
public class LoanApplicationController {
	
	@Autowired
	private LoanApplicationService loanService;
	
	@PostMapping("/apply")
	public ResponseEntity<String> loanApplication(LoanDto loanDto){
		return loanService.applyLoan(loanDto);
	}

}
