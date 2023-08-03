package com.springboot.blog.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springboot.blog.entity.Account;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.AccountDto;
import com.springboot.blog.repository.AccountRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.NewAccountCreationService;

public class NewAccountCreationServiceImpl implements NewAccountCreationService{

	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AccountRepository accountRepo;

	@Override
	public ResponseEntity<String> accountCreation(AccountDto accountDto) {
			Optional<User> user = Optional.ofNullable(accountDto.getUser());
			
			
		if( user.isEmpty()) {
			return new ResponseEntity<>("Please provide a user."
					,HttpStatus.BAD_REQUEST);  
		}else if(userRepo.existsByUsername(accountDto.getAccountName())) {
			return new ResponseEntity<>("User is not registered. Please signup first",HttpStatus.BAD_REQUEST);
		}
		
		
		
		else  {
			
			switch(accountDto.getAccountName().toLowerCase()) {
			
			case "checking" :
				
				if(accountRepo.existsByAccountName("Checking")&&accountRepo.existsByUser(user) ) {
					return new ResponseEntity<>("You already have an checkings account"
							,HttpStatus.BAD_REQUEST);
				}else {
					
					Account newAccount = new Account();
					newAccount.setAccountName("Checking");
					newAccount.setBalance(0);
					newAccount.setUser(user.get());
					accountRepo.save(newAccount);
					
					return new ResponseEntity<>("You successfully created an checkings account"
							, HttpStatus.OK);	
				}
				
			case "savings":
				if(accountRepo.existsByAccountName("Savings")&&accountRepo.existsByUser(user)) {
					return new ResponseEntity<>("You already have an savings account"
							,HttpStatus.BAD_REQUEST);
				}else {
					Account newAccount = new Account();
					newAccount.setAccountName("Savings");
					newAccount.setBalance(0);
					newAccount.setUser(accountDto.getUser());
					accountRepo.save(newAccount);
					
					return new ResponseEntity<>("You successfully created a savings account"
							, HttpStatus.OK);
				}
			
			case "investement":
				if(accountRepo.existsByAccountName("Savings")&&accountRepo.existsByUser(user)) {
					return new ResponseEntity<>("You already have an investement account"
							,HttpStatus.BAD_REQUEST);
				}else {
					Account newAccount = new Account();
					newAccount.setAccountName("Investement");
					newAccount.setBalance(0);
					newAccount.setUser(accountDto.getUser());
					accountRepo.save(newAccount);
					
					return new ResponseEntity<>("You successfully created a investement account"
							, HttpStatus.OK);
							}
			default:
				return new ResponseEntity<>("Please check all the details entered."
						,HttpStatus.BAD_REQUEST);
				
			}
		}
		
		
	
	}
	


}
