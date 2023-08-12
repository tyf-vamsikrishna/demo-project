package com.springboot.blog.payload;

import java.time.LocalDate;
import com.springboot.blog.enums.Gender;

import lombok.Data;

@Data
public class UserProfileDto {
	private String name;
	private String username;
	private String email;
	private String password;
	private int mobileNumber;
    private String address;
    private LocalDate dateOfBirth;
    private Gender gender;
}
