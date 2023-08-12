package com.springboot.blog.payload;


import com.springboot.blog.enums.AddressType;

import lombok.Data;

@Data
public class AddressDto {
	private AddressType addressType;
	private String line1;
	private String line2;
	private String city;
	private String state;
	private String pinCode;
}
