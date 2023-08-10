package com.springboot.blog.serviceimpl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.blog.aspect.AccountExistsException;
import com.springboot.blog.aspect.InvalidInputException;
import com.springboot.blog.aspect.UserNotRegisteredException;
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
		if( accountDto.getUser() == null) {   
			logger.info("The user is empty");
			throw new InvalidInputException("Please provide a registered user.");
		}else if(userRepo.existsByUsername(accountDto.getAccountName())) {
			logger.info("user is not empty. But is not registered");
			throw new UserNotRegisteredException("User is not registered. Please signup first");
		}
		else  {
			logger.info("user is not null and is registered");
			
			switch(accountDto.getAccountName().toLowerCase()) {
			
			case "checking" :
				logger.info("The account needed is checking");
				if(accountRepo.existsByAccountName("Checking")&&accountRepo.existsByUser(accountDto.getUser()) ) {
					throw new AccountExistsException("You already have an checkings account");
					
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
					throw new AccountExistsException("You already have a savings account");
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
					throw new AccountExistsException("You already have an investment account");
				}else {
					logger.info("Account creation process is started");
					Account newAccount = new Account();
					newAccount.setAccountName("Investement");
					newAccount.setBalance(0);
					newAccount.setUser(accountDto.getUser());
					accountRepo.save(newAccount);
					logger.info("Account creation process is completed");
					return new ResponseEntity<>("You successfully created a investement account."
							, HttpStatus.OK);
							}
			default:
				throw new InvalidInputException("Please check all the inputs");
				
			}
				}
			}
		}
	