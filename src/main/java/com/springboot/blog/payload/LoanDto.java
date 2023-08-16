package com.springboot.blog.payload;

import java.time.LocalDate;

import lombok.Data;

@Data
public class LoanDto {
	private String userName;
	private String fullName;
	private LocalDate DoB;
	private int mobileNumber;
	private String employementDetails;
	private long annualIncome;
	private long monthlyExpences;
	private String loanType;
	private long amount;
	private String purposeOfLoan;
	private long loanTerm;
}
