package com.springboot.blog.controller;

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

import com.springboot.blog.aspect.UserNotRegisteredException;
import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.springboot.blog")
class UserProfileControllerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(UserProfileControllerTest.class);
	
	
	@Autowired
	private UserProfileController userProfileController;
	
	@MockBean
	private UserRepository userRepo;
	
	private List<User> users;
	
	@BeforeEach
	void setUp() {
		logger.info("Set up is started.");
		users =List.of(new User(), new User(), new User()) ;
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
	}
	
	@Test
	void viewProfileTest() {
		logger.info("users repo created.");
		when(userRepo.existsByUsername(users.get(0).getUsername())).thenReturn(true);
		when(userRepo.findByUsername(users.get(0).getUsername())).thenReturn(Optional.ofNullable(users.get(0)));
		ResponseEntity<String> result = userProfileController.viewProfile(users.get(0).getUsername());
		assertEquals(new ResponseEntity<>(users.get(0).toString(),HttpStatus.OK) , result);
	}
	
	@Test
	void editNameTest() {
		logger.info("users repo created, Test is started.");
		when(userRepo.existsByUsername(users.get(0).getUsername())).thenReturn(true);
		when(userRepo.findByUsername(users.get(0).getUsername())).thenReturn(Optional.ofNullable(users.get(0)));
		ResponseEntity<String> result = userProfileController.editName(users.get(0).getUsername(), "New name");
		assertEquals(new ResponseEntity<>("Name changed.",HttpStatus.OK), result);
	}
	
	@Test
	void editName1Test() { //for user not exists
		logger.info("users repo created, Test is started.");
		when(userRepo.existsByUsername(users.get(0).getUsername())).thenReturn(false);
		when(userRepo.findByUsername(users.get(0).getUsername())).thenReturn(Optional.ofNullable(users.get(0)));
		assertThrows(UserNotRegisteredException.class, ()->userProfileController.editName(users.get(0).getUsername(), "New name"));
	}
	
	@Test
	void editPasswordTest() { 
		logger.info("users repo created, Test for password is started.");
		when(userRepo.existsByUsername(users.get(0).getUsername())).thenReturn(true);
		when(userRepo.findByUsername(users.get(0).getUsername())).thenReturn(Optional.ofNullable(users.get(0)));
		ResponseEntity<String> result = userProfileController.editPassword(users.get(0).getUsername(), "0", "New name");
		assertEquals(new ResponseEntity<>("Password changed.",HttpStatus.OK), result);
	}

}
