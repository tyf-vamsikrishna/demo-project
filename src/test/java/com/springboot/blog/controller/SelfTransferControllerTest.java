package com.springboot.blog.controller;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;

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

import com.springboot.blog.aspect.InsufficientFundsException;
import com.springboot.blog.aspect.InvalidInputException;
import com.springboot.blog.aspect.NotFoundException;
import com.springboot.blog.entity.Account;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.AccountDto;
import com.springboot.blog.repository.AccountRepository;
import com.springboot.blog.repository.TranscationRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.SelfTransferService;


@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.springboot.blog")
class SelfTransferControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(SelfTransferControllerTest.class);
	
	@MockBean
	private AccountRepository accountrepo;
	
	@MockBean
	private UserRepository userRepo;
	
	@MockBean
	private TranscationRepository transcationRepo;
	
	@Autowired
	private SelfTransferService selftransferService;
	
	private AccountDto fromAccountDto= new AccountDto();
	
	private AccountDto toAccountDto= new AccountDto();
	
	private List<User> users;
	private List<Account> accounts;
	
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
		when(userRepo.findAll()).thenReturn(users);
		
		logger.info("Account setup is satrted");
		accounts=List.of(new Account(), new Account(), new Account());
		logger.info("Starting to Set the user(1)'s checking account with balance 1000");
		accounts.get(0).setAccountName("Checking");
		accounts.get(0).setBalance(1000);
		accounts.get(0).setId(0);
		accounts.get(0).setUser(users.get(1));
		logger.info("Setting the account completed");
		logger.info("Starting to Set the user(1)'s savings account with balance 400");
		accounts.get(1).setAccountName("Savings");
		accounts.get(1).setBalance(400);
		accounts.get(1).setId(1);
		accounts.get(1).setUser(users.get(1));
		logger.info("Setting the account completed");
		logger.info("Starting to Set the user(0)'s savings account with balance 900");
		accounts.get(2).setAccountName("Savings");
		accounts.get(2).setBalance(900);
		accounts.get(2).setId(2);
		accounts.get(2).setUser(users.get(0));
		logger.info("Setting the account completed");
		logger.info("Account setup is completed");
		
		when(accountrepo.findAll()).thenReturn(accounts);
	}
	

	@Test
	void selfTransferTest() {
		//test for when everything is correct
		logger.info("The from and to account dto setup is started");
		fromAccountDto.setAccountDtoForTransfer(accounts.get(0));
		toAccountDto.setAccountDtoForTransfer(accounts.get(1));
		
		logger.info("The from and to account dto setup is completed");
		
		when(accountrepo.existsById(fromAccountDto.getId())).thenReturn(true);
		when(accountrepo.existsById(toAccountDto.getId())).thenReturn(true);
		when(accountrepo.findById(fromAccountDto.getId())).thenReturn(Optional.ofNullable(accounts.get(0)));
		when(accountrepo.findById(toAccountDto.getId())).thenReturn(Optional.ofNullable(accounts.get(1)));
		when(transcationRepo.findAll()).thenReturn(null);
		ResponseEntity<String> result = selftransferService.selfTransfer(fromAccountDto, toAccountDto, 300);
		assertEquals(new ResponseEntity<>("Funds transfered successfully",HttpStatus.OK),result);
		logger.info("Test is completd");	
	}
	
	@Test
	void selfTransfer2Test() {
		logger.info("Test for same accounts::The from and to account dto setup is started"); //test when both from and to accounts are same
		fromAccountDto.setAccountDtoForTransfer(accounts.get(0));
		toAccountDto.setAccountDtoForTransfer(accounts.get(0));
		logger.info("The from and to account dto setup is completed");
				
		when(accountrepo.existsById(fromAccountDto.getId())).thenReturn(true);
		when(accountrepo.existsById(toAccountDto.getId())).thenReturn(true);
		
		InvalidInputException e = assertThrows(InvalidInputException.class,()-> selftransferService.selfTransfer(fromAccountDto, toAccountDto, 300));
		assertEquals("Both the accounts are the same",e.getMessage());
		logger.info("Test is completd");
		
	}
	@Test
	void selfTransfer3test() {//test for insufficient balance
		logger.info("Test for insufficient balance:: The from and to account dto setup is started");
		fromAccountDto.setAccountDtoForTransfer(accounts.get(0));
		toAccountDto.setAccountDtoForTransfer(accounts.get(1));
		
		logger.info("The from and to account dto setup is completed");
		
		when(accountrepo.existsById(fromAccountDto.getId())).thenReturn(true);
		when(accountrepo.existsById(toAccountDto.getId())).thenReturn(true);
		
		InsufficientFundsException e = assertThrows(InsufficientFundsException.class,()-> selftransferService.selfTransfer(fromAccountDto, toAccountDto, 1300));
		assertEquals("Funds are not sufficient for the transfer",e.getMessage());
		logger.info("Test is completd");
		
	}
	@Test
	void selfTransfer4test() {//test for different users and diff accounts
		logger.info("Test for different users:: The from and to account dto setup is started");
		fromAccountDto.setAccountDtoForTransfer(accounts.get(0));
		toAccountDto.setAccountDtoForTransfer(accounts.get(2));
		
		logger.info("The from and to account dto setup is completed");
		
		when(accountrepo.existsById(fromAccountDto.getId())).thenReturn(true);
		when(accountrepo.existsById(toAccountDto.getId())).thenReturn(true);
		
		InvalidInputException e = assertThrows(InvalidInputException.class,()-> selftransferService.selfTransfer(fromAccountDto, toAccountDto, 300));
		assertEquals("This is for self transfer. Please make sure both the accounts are for the same user",e.getMessage());
		logger.info("Test is completd");
		
	}
	@Test
	void selfTransfer5test() {//From account does not exist
		logger.info("Test for different users:: The from and to account dto setup is started");
		fromAccountDto.setAccountName("Investment");
		fromAccountDto.setBalance(11);
		fromAccountDto.setId(3);
		fromAccountDto.setUser(users.get(0));
		toAccountDto.setAccountDtoForTransfer(accounts.get(1));
		logger.info("The from and to account dto setup is completed");
		when(accountrepo.existsById(fromAccountDto.getId())).thenReturn(false);
		when(accountrepo.existsById(toAccountDto.getId())).thenReturn(true);
		
		NotFoundException e = assertThrows(NotFoundException.class,()-> selftransferService.selfTransfer(fromAccountDto, toAccountDto, 300));
		assertEquals("From account does not exist",e.getMessage());
		logger.info("Test is completd");
		
	}
	

}
