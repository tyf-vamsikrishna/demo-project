package com.springboot.blog.entity;

import java.time.LocalDate;

import com.springboot.blog.enums.LoanStatus;
import com.springboot.blog.enums.LoanType;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(name="loans",uniqueConstraints=
@UniqueConstraint(columnNames= {"id"}))
public class Loan {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private User user;
	
	private String fullName;
	private LocalDate DoB;
	private int mobileNumber;
	private String employementDetails;
	private long annualIncome;
	private long monthlyExpences;
	private LoanType loanType;
	private long amount;
	private String purposeOfLoan;
	private long loanTerm;
	private LoanStatus status;
}
