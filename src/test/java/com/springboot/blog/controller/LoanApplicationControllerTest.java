package com.springboot.blog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.blog.entity.User;
import com.springboot.blog.payload.LoanDto;
import com.springboot.blog.repository.AccountRepository;
import com.springboot.blog.repository.LoanRepository;
import com.springboot.blog.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.springboot.blog")
class LoanApplicationControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(LoanApplicationControllerTest.class);
	
	@MockBean
	private AccountRepository accountrepo;
	
	@MockBean
	private UserRepository userRepo;
	
	@Autowired
	private LoanApplicationController loanApplicationController;
	
	@MockBean
	private LoanRepository loanRepo;
	
	private LoanDto loanDto= new LoanDto();
	private List<User> users;

	@Test
	void LoanApplicationControllertest() {
		logger.info("The list setup is started");
		users = List.of(new User() , new User(), new User());
		logger.info("useres list is formed");
		for(int i=0;i<users.size();i++) {
			User user = users.get(i);
			user.setId(i);
			user.setEmail(i+"@gmail.com");
			user.setName(i+"");
			user.setPassword(i+"");
			user.setUsername(i+"");
		}
		logger.info(users.toString());
		when(userRepo.findAll()).thenReturn(users);
		
		loanDto.setAmount(1000);
		loanDto.setAnnualIncome(100000);
		loanDto.setDoB(LocalDate.of(2000, 02, 20));
		loanDto.setUserName(users.get(0).getUsername());
		loanDto.setLoanType("personal");
		
		when(userRepo.existsByUsername(users.get(0).getUsername())).thenReturn(true);
		when(userRepo.findByUsername(users.get(0).getUsername())).thenReturn( Optional.ofNullable( users.get(0)));
		
		ResponseEntity<String> result = loanApplicationController.loanApplication(loanDto);
		
		assertEquals(new ResponseEntity<>("Loan Applied Successfully",HttpStatus.OK) , result);
	}
	
	

}
