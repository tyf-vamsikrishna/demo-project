package com.springboot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

}
