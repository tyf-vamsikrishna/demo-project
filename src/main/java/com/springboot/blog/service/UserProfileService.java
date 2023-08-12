package com.springboot.blog.service;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.blog.payload.AddressDto;

@Service
public interface UserProfileService {
	ResponseEntity<String> viewProfile(String userName);
	
	ResponseEntity<String> editName(String userName, String name);
	ResponseEntity<String> editEmail(String userName,String Password, String email);
	ResponseEntity<String> editPassword(String userName, String oldPassword, String newPassword);
	ResponseEntity<String> editUsername(String userName, String newUserName);
	ResponseEntity<String> editMobileNumber(String userName, int mobileNumber);
	ResponseEntity<String> editCurrentAddress(String userName, AddressDto currentAddressDto);
	ResponseEntity<String> editGender(String userName,String gender);
	ResponseEntity<String> editBillingAddress(String userName, AddressDto billingAddressDto);
	ResponseEntity<String> editDateOfBirth(String userName, LocalDate dob);
}
