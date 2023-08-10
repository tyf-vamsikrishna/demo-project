package com.springboot.blog;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.springboot.blog.aspect.NotFoundException;
import com.springboot.blog.controller.AccountsController;
import com.springboot.blog.entity.Account;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.AccountDto;
import com.springboot.blog.repository.AccountRepository;
import com.springboot.blog.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.springboot.blog")
class SpringbootBlogRestApiApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(SpringbootBlogRestApiApplicationTests.class);
	
	@Autowired
	private AccountsController accountController;
	
	private AccountDto accountDto = new AccountDto();
	
	@MockBean
	private UserRepository userRepo ;
	
	@MockBean
	private AccountRepository accountRepo;
	
	private List<User> users;
	private List<Account> accounts;
	
	@BeforeEach
	void setUp() {
		logger.info("The setup is started");
		users = List.of(new User() , new User(), new User());
		logger.info("useres list is formed");
		for(int i=0;i<users.size();i++) {
			User user = users.get(i);
			user.setId(i);
			user.setEmail(i+"@gmail.com");
			user.setName(i+"");
			user.setPassword(i+"");
			user.setUsername(i+"");
			
			when(userRepo.findAll()).thenReturn(users);
			
			logger.info("Account list is starrting");
			accounts = List.of(new Account());
			accounts.get(0).setAccountName("Checking");
			accounts.get(0).setBalance(0);
			accounts.get(0).setUser(users.get(0));
			accounts.get(0).setId(1);
			logger.info("Account list is completed");
			when(accountRepo.findAll()).thenReturn(accounts);
		}
	}
	
	
	
//From here  the test cases are for the new account creation.
	@Test
	void accountCreationTest() {
		logger.info("Account creation Test started");
		
		accountDto.setAccountName("Savings");
		accountDto.setBalance(0);
		accountDto.setUser(users.get(1));
		when(userRepo.existsByUsername(accountDto.getUser().getUsername())).thenReturn(true);
		logger.info("Getting the response entity");
		ResponseEntity<String> result = accountController.accountCreation(accountDto);
				
		assertEquals(new ResponseEntity<>("You successfully created a savings account"
				, HttpStatus.OK),result);
			logger.info("Test completed successfully");	
	}
	
	
	
	
	//From here  the test cases are for the  account overview.

	@Test
	void accountOverViewTest() {  //testcase for when the user and account exists 
		logger.info("Account over view test is started");
				
		String username = "0";
		
		when(userRepo.findByUsername(username)).thenReturn(Optional.ofNullable(accounts.get(0).getUser()) );
		when(accountRepo.findByUser(users.get(0))).thenReturn(Optional.ofNullable(accounts.get(0)) );		
		ResponseEntity<String> result = accountController.accountOverview(username);
	
		String summary = "";
		summary+= " \n" +accounts.get(0).toString();
		assertEquals(new ResponseEntity<>("Over view of accounts: \n"+summary ,HttpStatus.OK) , result);
		logger.info("AccountOverview test is completed");
	}
	
	@Test
	void accountOverView1Test() {   //testcase for when the does not exists 
		logger.info("Account over view test is started");
		
		String username = "Name";
		
		when(userRepo.findByUsername(username)).thenReturn(Optional.empty());
		when(accountRepo.findByUser(users.get(0))).thenReturn(Optional.ofNullable(accounts.get(0)) );		
	
		assertThrows(NotFoundException.class, () -> accountController.accountOverview(username));
		logger.info("AccountOverview test is completed");
	}
	
	@Test
	void accountOverView2Test() {  //testcase when the user exists and account does not exists 
		logger.info("Account over view test is started");
				
		String username = "1";
		
		when(userRepo.findByUsername(username)).thenReturn(Optional.ofNullable(users.get(1)) );
		when(accountRepo.findByUser(users.get(1))).thenReturn(Optional.empty() );		
		ResponseEntity<String> result = accountController.accountOverview(username);
	
		assertEquals(new ResponseEntity<>("You have no account with us. Please create one" ,HttpStatus.OK) , result);
		logger.info("AccountOverview test is completed");
	}
	

}
