package com.springboot.blog.serviceimpl;


import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.springboot.blog.aspect.AuthenticationFailedException;
import com.springboot.blog.aspect.UserNotRegisteredException;
import com.springboot.blog.entity.Address;
import com.springboot.blog.entity.User;
import com.springboot.blog.enums.Gender;
import com.springboot.blog.payload.AddressDto;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.UserProfileService;

@Service
public class UserProfileServiceImpl implements UserProfileService{
	private static final Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	

	@Override
	public ResponseEntity<String> viewProfile(String username) {
		logger.info("Upto controller is fine and got username");
		if(userRepo.existsByUsername(username)) {
			logger.info("User exists in repo");
			User user = userRepo.findByUsername(username).get();
			//System.out.println(user.toString());
			logger.info("Found user and sending user profile");
			return new ResponseEntity<>(user.toString(),HttpStatus.OK);
		}else {
			
			logger.error("The user name is not valid or user is  not registered");
			throw new UserNotRegisteredException("The username is not valid or is not registered.");
		}
	}


	@Override
	public ResponseEntity<String> editName(String userName, String name) {
		logger.info("Upto controller is fine and got username");
		if(userRepo.existsByUsername(userName)) {
			logger.info("User exists in repo");
			User user = userRepo.findByUsername(userName).get();
			user.setName(name);
			logger.info("Name changed");
			return new ResponseEntity<>("Name changed.",HttpStatus.OK);
			
		}else {
			logger.error("The user name is not valid or user is  not registered");
			throw new UserNotRegisteredException("The username is not valid or is not registered.");
		}
	}

	@Override
	public ResponseEntity<String> editEmail(String userName, String Password, String email) {
		if(userRepo.existsByUsername(userName)) {
			logger.info("User exists in repo");
			try {
				Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
		                userName, Password));
				User user = userRepo.findByUsername(userName).get();
				user.setEmail(email);
				logger.info("Email changed");
				return new ResponseEntity<>("Email changed.",HttpStatus.OK);
				
			}catch(AuthenticationFailedException e) {
				throw new AuthenticationFailedException("Check the username and password.");
			}
			
		}else {
			logger.error("The user name is not valid or user is  not registered");
			throw new UserNotRegisteredException("The username is not valid or is not registered.");
		}
	}

	
	@Override
	public ResponseEntity<String> editPassword(String userName, String oldPassword, String newPassword) {
		if(userRepo.existsByUsername(userName)) {
			logger.info("User exists in repo");
			try {
				logger.info("Starting acuthentication");
//				Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//		                userName, oldPassword));
				logger.info("Acuthentication success");
				User user = userRepo.findByUsername(userName).get();
				user.setPassword(newPassword);
				logger.info("Password changed");
				return new ResponseEntity<>("Password changed.",HttpStatus.OK);
				
			}catch(AuthenticationFailedException e) {
				logger.info("Acuthentication failed");
				throw new AuthenticationFailedException("Check the username and password.");
			}
			
		}else {
			logger.error("The user name is not valid or user is  not registered");
			throw new UserNotRegisteredException("The username is not valid or is not registered.");
		}
	}

	@Override
	public ResponseEntity<String> editUsername(String userName, String newUserName) {
		if(userRepo.existsByUsername(userName)) {
			logger.info("User exists in repo");
			User user = userRepo.findByUsername(userName).get();
			user.setUsername(newUserName);
			logger.info("Username changed");
			return new ResponseEntity<>("Username changed.",HttpStatus.OK);
		}else {
			logger.error("The user name is not valid or user is  not registered");
			throw new UserNotRegisteredException("The username is not valid or is not registered.");
		}
		
	}

	@Override
	public ResponseEntity<String> editMobileNumber(String userName, int mobileNumber) {
		if(userRepo.existsByUsername(userName)) {
			logger.info("User exists in repo");
			User user = userRepo.findByUsername(userName).get();
			user.setMobileNumber(mobileNumber);
			logger.info("Mobile Number is changed");
			return new ResponseEntity<>("Mobile Number changed.",HttpStatus.OK);
		}else {
			logger.error("The user name is not valid or user is  not registered");
			throw new UserNotRegisteredException("The username is not valid or is not registered.");
		}
	}

	@Override
	public ResponseEntity<String> editCurrentAddress(String userName, AddressDto currentAddressDto) {
		if(userRepo.existsByUsername(userName)) {
			logger.info("User exists in repo");
			User user = userRepo.findByUsername(userName).get();
			Address address = new Address();
			address.setAddressFromDto(currentAddressDto);
			address.setUser(user);
			user.setCurrentAddress(address);
			logger.info("Current Address is changed");
			return new ResponseEntity<>("Current address changed.",HttpStatus.OK);
		}else {
			logger.error("The user name is not valid or user is  not registered");
			throw new UserNotRegisteredException("The username is not valid or is not registered.");
		}
	}

	@Override
	public ResponseEntity<String> editGender(String userName, String gender) {
		if(userRepo.existsByUsername(userName)) {
			logger.info("User exists in repo");
			User user = userRepo.findByUsername(userName).get();
			switch (gender){
			case "Male" :
				user.setGender(Gender.MALE);
			case "Female":
				user.setGender(Gender.FEMALE);
			case "Others":
				user.setGender(Gender.OTHERS);
			}
			
			logger.info("Gender is changed");
			return new ResponseEntity<>("Gender changed.",HttpStatus.OK);
		}else {
			logger.error("The user name is not valid or user is  not registered");
			throw new UserNotRegisteredException("The username is not valid or is not registered.");
		}
		
	}

	@Override
	public ResponseEntity<String> editBillingAddress(String userName, AddressDto billingAddressDto) {
		if(userRepo.existsByUsername(userName)) {
			logger.info("User exists in repo");
			User user = userRepo.findByUsername(userName).get();
			Address address = new Address();
			address.setAddressFromDto(billingAddressDto);
			address.setUser(user);
			user.setCurrentAddress(address);
			logger.info("Billing Address is changed");
			return new ResponseEntity<>("Billing address changed.",HttpStatus.OK);
		}else {
			logger.error("The user name is not valid or user is  not registered");
			throw new UserNotRegisteredException("The username is not valid or is not registered.");
		}
	}

	@Override
	public ResponseEntity<String> editDateOfBirth(String userName, LocalDate dob) {
		if(userRepo.existsByUsername(userName)) {
			logger.info("User exists in repo");
			User user = userRepo.findByUsername(userName).get();
			user.setDateOfBirth(dob);
			logger.info("Date of birth is changed");
			return new ResponseEntity<>("Date of birth changed.",HttpStatus.OK);
			
		}else {
			logger.error("The user name is not valid or user is  not registered");
			throw new UserNotRegisteredException("The username is not valid or is not registered.");
		}
	}

}
