package com.springboot.blog.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.AddressDto;
import com.springboot.blog.payload.UserProfileDto;
import com.springboot.blog.service.UserProfileService;

import io.micrometer.common.lang.NonNull;



@RestController
@RequestMapping("/api/userprofile")
public class UserProfileController {
	@Autowired
	private UserProfileService userProfileService;
	
	
	
	@GetMapping("/view")
	public ResponseEntity<String> viewProfile(@RequestBody @NonNull String userName){
		return userProfileService.viewProfile(userName);
	}
	
	@RequestMapping("/edit/name")
	public ResponseEntity<String> editName(@RequestBody @NonNull String userName,String newName){
		return userProfileService.editName(userName,newName);
	}
	
	@RequestMapping("/edit/username")
	public ResponseEntity<String> editUsername(@RequestBody @NonNull String userName,String newUsername){
		return userProfileService.editUsername(userName,newUsername);
	}
	
	@RequestMapping("/edit/email")
	public ResponseEntity<String> editEmail(@RequestBody @NonNull String userName,String password,String newEmail){
		return userProfileService.editEmail(userName,password,newEmail);
	}
	
	@RequestMapping("/edit/password")
	public ResponseEntity<String> editPassword(@RequestBody @NonNull String userName,String oldPassword,String newPassword){
		return userProfileService.editPassword(userName,oldPassword,newPassword);
	}
	
	@RequestMapping("/edit/dob")
	public ResponseEntity<String> editDateofBirth(@RequestBody @NonNull String userName,LocalDate newDob){
		return userProfileService.editDateOfBirth(userName,newDob);
	}
	
	@RequestMapping("/edit/gender")
	public ResponseEntity<String> editGender(@RequestBody @NonNull String userName,String gender){
		return userProfileService.editGender(userName,gender);
	}
	
	@RequestMapping("/edit/billingaddress")
	public ResponseEntity<String> editBillingAddress(@RequestBody @NonNull String userName,AddressDto addressDto){
		return userProfileService.editBillingAddress(userName,addressDto);
	}
	
	@RequestMapping("/edit/currentaddress")
	public ResponseEntity<String> editCurrentAddress(@RequestBody @NonNull String userName,AddressDto addressDto){
		return userProfileService.editCurrentAddress(userName,addressDto);
	}
	
	@RequestMapping("/edit/mobilenumber")
	public ResponseEntity<String> editMobileNumber(@RequestBody @NonNull String userName,int newMobileNumber){
		return userProfileService.editMobileNumber(userName,newMobileNumber);
	}
}
