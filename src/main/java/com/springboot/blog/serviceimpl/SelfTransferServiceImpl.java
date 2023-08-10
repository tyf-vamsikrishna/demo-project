package com.springboot.blog.serviceimpl;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.blog.aspect.InsufficientFundsException;
import com.springboot.blog.aspect.InvalidInputException;
import com.springboot.blog.aspect.NotFoundException;
import com.springboot.blog.entity.Transcation;
import com.springboot.blog.enums.TranscationType;
import com.springboot.blog.payload.AccountDto;
import com.springboot.blog.repository.AccountRepository;
import com.springboot.blog.repository.TranscationRepository;
import com.springboot.blog.service.SelfTransferService;



@Service
public class SelfTransferServiceImpl implements SelfTransferService{
	private static final Logger logger = LoggerFactory.getLogger(SelfTransferServiceImpl.class);
	
	@Autowired
	private AccountRepository accountRepo;
	
	@Autowired
	TranscationRepository transcationRepo;

	@Override
	public ResponseEntity<String> selfTransfer(AccountDto from, AccountDto to,long amount) { 
		logger.info("Got from, to and amount for self transfer");
		if(accountRepo.existsById(from.getId()) && accountRepo.existsById(to.getId())) { //checking if both the accounts exist
			if(from.getUser().equals(to.getUser())&&from.getUser()!=null) { //checking if the users are same
				if(from.getBalance()>=amount&& !from.equals(to)) { //checking if balance is sufficient and both the accounts are not same 
					logger.info("Transfer initiated");
					from.setBalance(from.getBalance()-amount);
					Transcation fromTransction = new Transcation();
					fromTransction.setAccount(accountRepo.findById(from.getId()).get());
					fromTransction.setAmount(amount);
					fromTransction.setTimeOfTranscation(LocalDateTime.now());
					fromTransction.setTranscationType(TranscationType.DEBIT);
					to.setBalance(amount+to.getBalance());
					transcationRepo.save(fromTransction);
					Transcation toTransction = new Transcation();
					toTransction.setAccount(accountRepo.findById(to.getId()).get());
					toTransction.setAmount(amount);
					toTransction.setTimeOfTranscation(LocalDateTime.now());
					toTransction.setTranscationType(TranscationType.CREDIT);
					transcationRepo.save(toTransction);
					logger.info("Tranfer completed");
					return new ResponseEntity<>("Funds transfered successfully",HttpStatus.OK);
				}else {
					if(!from.equals(to)) { //if the funds are not enough
						logger.error("Insufficient funds");
						throw new InsufficientFundsException("Funds are not sufficient for the transfer");
					}else {  //if both the accounts are same
						logger.error("Both the accounts are same");
						throw new InvalidInputException("Both the accounts are the same");
					}
				}
			}else {
				logger.info("Not same user or User Does not exist");
				String message=from.getUser()==to.getUser()?
						"Please enter a valid user":
							"This is for self transfer. Please make sure both the accounts are for the same user";
				throw new InvalidInputException(message);
			}
		}else {
			logger.info("Accounts/Account does not exist");
			String message = accountRepo.existsById(from.getId())?
					"Destination account doesnot exits":"From account does not exist";
			throw new NotFoundException(message);
		}
		
	}

	
}
