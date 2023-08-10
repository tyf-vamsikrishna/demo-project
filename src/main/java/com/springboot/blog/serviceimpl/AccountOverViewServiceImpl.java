package com.springboot.blog.serviceimpl;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.springboot.blog.aspect.NotFoundException;
import com.springboot.blog.entity.Account;
import com.springboot.blog.entity.User;
import com.springboot.blog.repository.AccountRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.AccountOverViewService;

@Service
public class AccountOverViewServiceImpl implements AccountOverViewService {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountOverViewServiceImpl.class);
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AccountRepository accountRepo;

	@Override
	public ResponseEntity<String> accountOverview(String username) {
		Optional<User> user = userRepo.findByUsername(username);
		logger.info("Optional user created with the username");
		
		if(user.isEmpty()) {
			logger.info("The user is empty");
			throw new NotFoundException("No user found with the username: "+username);
		}else {
			logger.info("The user is not empty and fetching all the accounts."); 
			
		    List<Account> accounts = accountRepo.findByUser(user.get())
		    		.stream()
		    		.collect(Collectors.toList());
		    if(accounts.isEmpty()) {
		    	logger.info("No accounts found for the user"); 
		    	throw new NotFoundException("You have no account with us. Please create one");
		    }else {
		    	String summary="";
			    logger.info("Accounts found for the user. Starting to get the overview");
			    for (Account account : accounts) {
			    	summary+= " \n" +account.toString();
			    }
				logger.info("The account overview is fetched sucessfully");
				return new ResponseEntity<>("Over view of accounts: \n"+summary ,HttpStatus.OK);
		    	
		    }
		     
		}
		
	}
	

}
