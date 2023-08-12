package com.springboot.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entity.Address;
import com.springboot.blog.entity.User;

public interface AddressRepository extends JpaRepository<Address,Integer> {
	boolean existsByUser(User user);
	Optional<Address> findByUser(User user);
}
