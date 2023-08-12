package com.springboot.blog.entity;

import org.hibernate.annotations.GeneratorType;

import com.springboot.blog.enums.AddressType;
import com.springboot.blog.payload.AddressDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(name="addresses", 
uniqueConstraints=@UniqueConstraint(columnNames= {"id"}))
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private AddressType addressType;
	private String line1;
	private String line2;
	private String city;
	private String state;
	private String pinCode;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	
	public Address setAddressFromDto(AddressDto addressDto) {
		Address address = new Address();
		address.setAddressType(addressDto.getAddressType());
		address.setCity(addressDto.getCity());
		address.setLine1(addressDto.getLine1());
		address.setLine2(addressDto.getLine2());
		address.setPinCode(addressDto.getPinCode());
		address.setState(addressDto.getState());
		return address;
	}
}
