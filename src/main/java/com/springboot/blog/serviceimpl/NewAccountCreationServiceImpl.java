package com.springboot.blog.serviceimpl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Account;
import com.springboot.blog.payload.AccountDto;
import com.springboot.blog.repository.AccountRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.NewAccountCreationService;

@Service
public class NewAccountCreationServiceImpl implements NewAccountCreationService{
	
	private static final Logger logger = LoggerFactory.getLogger(NewAccountCreationServiceImpl.class);

	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AccountRepository accountRepo;

	@Override
	public ResponseEntity<String> accountCreation(AccountDto accountDto) {
		logger.info("Got account dto");
			
			logger.info("Created the optional user from input user");
			
		if( accountDto.getUser() == null) {   
			logger.info("The user is empty");
			return new ResponseEntity<>("Please provide a user."
					,HttpStatus.BAD_REQUEST);  
		}else if(userRepo.existsByUsername(accountDto.getAccountName())) {
			logger.info("user is not empty. But is not registered");
			return new ResponseEntity<>("User is not registered. Please signup first",HttpStatus.OK);
		}
		
		
		
		else  {
			logger.info("user is not null and is registered");
			
			switch(accountDto.getAccountName().toLowerCase()) {
			
			case "checking" :
				logger.info("The account needed is checking");
				if(accountRepo.existsByAccountName("Checking")&&accountRepo.existsByUser(accountDto.getUser()) ) {
					return new ResponseEntity<>("You already have an checkings account"
							,HttpStatus.OK);
					
				}else {
					logger.info("Account creation process is started");
					Account newAccount = new Account();
					newAccount.setAccountName("Checking");
					newAccount.setBalance(0);
					newAccount.setUser(accountDto.getUser());
					accountRepo.save(newAccount);
					
					logger.info("Account creation process is completed");
					return new ResponseEntity<>("You successfully created an checkings account"
							, HttpStatus.OK);	
				}
				
			case "savings":
				logger.info("The account needed is savings");
				if(accountRepo.existsByAccountName("Savings")&&accountRepo.existsByUser(accountDto.getUser())) {
					logger.info("Account already found in repo");
					return new ResponseEntity<>("You already have an savings account"
							,HttpStatus.OK);
				}else {
					logger.info("Account creation process is started");
					Account newAccount = new Account();
					newAccount.setAccountName("Savings");
					newAccount.setBalance(0);
					newAccount.setUser(accountDto.getUser());
					accountRepo.save(newAccount);
					logger.info("Account creation process is completed");
					return new ResponseEntity<>("You successfully created a savings account"
							, HttpStatus.OK);
				}
			
			case "investement":
				logger.info("The account needed is investment");
				if(accountRepo.existsByAccountName("Savings")&&accountRepo.existsByUser(accountDto.getUser())) {
					logger.info("Account already found in repo");
					return new ResponseEntity<>("You already have an investement account"
							,HttpStatus.BAD_REQUEST);
				}else {
					logger.info("Account creation process is started");
					Account newAccount = new Account();
					newAccount.setAccountName("Investement");
					newAccount.setBalance(0);
					newAccount.setUser(accountDto.getUser());
					accountRepo.save(newAccount);
					logger.info("Account creation process is completed");
					return new ResponseEntity<>("You successfully created a investement account"
							, HttpStatus.OK);
							}
			default:
				return new ResponseEntity<>("Please check all the details entered."
						,HttpStatus.OK);
				
			}
		}
		
		
	
	}
	


}
