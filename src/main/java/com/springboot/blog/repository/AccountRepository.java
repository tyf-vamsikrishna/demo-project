package com.springboot.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.springboot.blog.entity.Account;
import com.springboot.blog.entity.User;

@Component
public interface AccountRepository extends JpaRepository<Account, Long > {
	Optional<Account> findByUser(User user);
	boolean existsByUser(User user);
	boolean existsByAccountName(String accountname);
}




