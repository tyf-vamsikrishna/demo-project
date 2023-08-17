package com.springboot.blog.serviceimpl;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.blog.aspect.InvalidRole;
import com.springboot.blog.aspect.UserNotRegisteredException;
import com.springboot.blog.entity.Loan;
import com.springboot.blog.entity.User;
import com.springboot.blog.enums.LoanStatus;
import com.springboot.blog.enums.LoanType;
import com.springboot.blog.payload.LoanDto;
import com.springboot.blog.repository.LoanRepository;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.LoanApplicationService;



@Service
public class LoanApplicationServiceImpl implements LoanApplicationService{

	private static final Logger logger = LoggerFactory.getLogger(LoanApplicationServiceImpl.class);
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private LoanRepository loanRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	

	@Override
	public ResponseEntity<String> applyLoan(LoanDto loanDto) {
		if(userRepo.existsByUsername(loanDto.getUserName())) {
			Loan loan = new Loan();
			loan.setAmount(loanDto.getAmount());
			loan.setAnnualIncome(loanDto.getAnnualIncome());
			loan.setDoB(loanDto.getDoB());
			loan.setEmployementDetails(loanDto.getEmployementDetails());
			loan.setFullName(loanDto.getFullName());
			loan.setLoanTerm(loanDto.getLoanTerm());
			
			switch(loanDto.getLoanType()) {
			case"personal":
				loan.setLoanType(LoanType.PERSONAL);
			case"auto":
				loan.setLoanType(LoanType.AUTO);
			case"mortigage":
				loan.setLoanType(LoanType.MORTIGAGE);
			}
			
			loan.setMobileNumber(loanDto.getMobileNumber());
			loan.setMonthlyExpences(loanDto.getMonthlyExpences());
			loan.setPurposeOfLoan(loanDto.getPurposeOfLoan());
			loan.setStatus(LoanStatus.APPLIED);
			loan.setUser(userRepo.findByUsername(loanDto.getUserName()).get());
			logger.info("It is ok up to saving");
			loanRepo.save(loan);
			logger.info("It is ok up to saving");
			return new ResponseEntity<>("Loan Applied Successfully",HttpStatus.OK);
		}else {
			throw new UserNotRegisteredException("User not registered or invalid username");
		}
	}


	@Override
	public ResponseEntity<String> view(String username, int loanId) {
		logger.info("Checking if user is empty ");
		if(! userRepo.findByUsername(username).isEmpty()) {
			logger.info("user is not empty");
			System.out.println(roleRepo.getById(1l));
			System.out.println(userRepo.findByUsername(username).get().getRoles());
			System.out.println(userRepo.findByUsername(username).get().getRoles().contains(roleRepo.getById(1l)));
			if(userRepo.findByUsername(username).get().getRoles().contains(roleRepo.getById(1l))) {
				logger.info("user  role is  enough");
				Loan loan = loanRepo.getById(loanId);
				String loanApplication = loan.toString();
				System.out.println(loanApplication);
				return new ResponseEntity<>("Loan application fetched: "+loanApplication,HttpStatus.OK);
			}else {
				throw new InvalidRole("User role is not sufficient for the approval");
			}
			
		}else {
			logger.info(" user is empty ");
			throw new UserNotRegisteredException("User not registered or invalid username");
		}
	}


	@Override
	public ResponseEntity<String> approve(String username, int loanId) {
		Optional<User> user = userRepo.findByUsername(username);
		if(! user.isEmpty()) {
			if(user.get().getRoles().contains(roleRepo.getOne(1l))) {
				Loan loan = loanRepo.getById(loanId);
				loan.setStatus(LoanStatus.APPROVED);
				return new ResponseEntity<>("Loan Approved",HttpStatus.OK);
			}else {
				throw new InvalidRole("User role is sufficient for the approval");
			}
		}else {
			throw new UserNotRegisteredException("User not registered or invalid username");
		}
		
	}

}
