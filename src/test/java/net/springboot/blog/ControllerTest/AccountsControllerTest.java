package net.springboot.blog.ControllerTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.springboot.blog.controller.AccountsController;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.AccountDto;
import com.springboot.blog.repository.UserRepository;


@DataJpaTest
@SpringBootTest(classes = AccountsController.class)
class AccountsControllerTest {
	
	private AccountsController accountsController;
	private AccountDto accountDto;
	
	@Autowired
	private UserRepository userRepo;
	
	@BeforeAll
	 void setUp() {
		accountsController= new AccountsController();
		accountDto= new AccountDto();
		
	}

	@Test
	void accountCreationTest() {
		
		
		accountDto.setAccountName("Savings");
		accountDto.setBalance(0);
		accountDto.setUser(userRepo.findById(2l).orElse(new User()));
		
		ResponseEntity<String> result = accountsController.accountCreation(accountDto);
				
		assertEquals(new ResponseEntity<>("You successfully created a savings account"
				, HttpStatus.OK),result);
				
	}

}
