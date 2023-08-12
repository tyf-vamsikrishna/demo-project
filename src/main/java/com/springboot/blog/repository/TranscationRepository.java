package com.springboot.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entity.Account;
import com.springboot.blog.entity.Transcation;

public interface TranscationRepository extends JpaRepository<Transcation,Integer>{
	 boolean existsByTransctionId(int transcationId); 
	 List<Transcation> findByAccount(Account account);
}
