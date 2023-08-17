package com.springboot.blog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
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

import com.springboot.blog.entity.Loan;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.enums.LoanType;
import com.springboot.blog.payload.LoanDto;
import com.springboot.blog.repository.AccountRepository;
import com.springboot.blog.repository.LoanRepository;
import com.springboot.blog.repository.RoleRepository;
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
	
	@MockBean
	private RoleRepository roleRepo;
	
	private LoanDto loanDto= new LoanDto();
	private List<User> users;
	
	@BeforeEach
	void setUp() {
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
		users.get(1).setRoles(null);
		logger.info(users.toString());
		when(userRepo.findAll()).thenReturn(users);
		
	}

	@Test
	void loanApplicationControllertest() {
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
	
	@Test
	void viewLoanTest() {
		List<Role> roles = new ArrayList();
		Role role = new Role();
		role.setId(0);
		role.setName("USER");
		
		Role role1 = new Role();
		role1.setId(1);
		role1.setName("ADMIN");
		roles.add(role);
		roles.add(role1);
		
		
		when(roleRepo.findAll()).thenReturn(roles);
		users.get(1).setRoles(Set.of(role1));
		Loan loan = new Loan();
		loan.setAmount(1000);
		loan.setAnnualIncome(100000);
		loan.setDoB(LocalDate.of(2000, 02, 20));
		loan.setUser(users.get(0));
		loan.setLoanType(LoanType.PERSONAL);
		
		List<Loan> loans = List.of(loan);
		logger.info("Loan is created");
		when(userRepo.existsByUsername(users.get(1).getUsername())).thenReturn(true);
		when(userRepo.findByUsername(users.get(1).getUsername())).thenReturn( Optional.ofNullable(users.get(1)));
		when(loanRepo.findAll()).thenReturn(loans);
		when(roleRepo.getById(1l)).thenReturn(role1);
		when(loanRepo.getById(0)).thenReturn(loan);
		logger.info("Upto response is fine");
		ResponseEntity<String> result = loanApplicationController.viewLoan(users.get(1).getUsername() ,0);
		logger.info("Response is fine");
		String loanApplication = loan.toString();
		assertEquals(new ResponseEntity<>("Loan application fetched: "+loanApplication,HttpStatus.OK) , result);
		
	}

}
