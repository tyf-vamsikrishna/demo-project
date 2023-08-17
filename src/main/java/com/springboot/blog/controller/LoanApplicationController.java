package com.springboot.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.LoanDto;
import com.springboot.blog.service.LoanApplicationService;


import io.micrometer.common.lang.NonNull;

@RestController
@RequestMapping("/api/loan")
public class LoanApplicationController {
	
	@Autowired
	private LoanApplicationService loanService;
	
	
	@PostMapping("/apply")
	public ResponseEntity<String> loanApplication(@RequestBody LoanDto loanDto){
		return loanService.applyLoan(loanDto);
	}
	
	@GetMapping("/view")
	public ResponseEntity<String> viewLoan(@RequestBody @NonNull String userName,@NonNull int loanId){
		return loanService.view(userName, loanId);
	}
	
	@PutMapping("/approve")
	public ResponseEntity<String> loanApprove(@RequestBody @NonNull String userName,@NonNull int loanId){
		return loanService.approve(userName, loanId);
	}

}
