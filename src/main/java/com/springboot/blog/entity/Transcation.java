package com.springboot.blog.entity;

import java.time.LocalDateTime;

import com.springboot.blog.enums.TranscationType;

import jakarta.persistence.Column;
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
@Table(name="Transcations", uniqueConstraints=
@UniqueConstraint(columnNames= {"transcationId"}))
public class Transcation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int transcationId;
	
	@ManyToOne
	@JoinColumn(name="accountId",nullable=false)
	private Account account;
	
	@Column(nullable = false)
	private TranscationType transcationType;
	
	@Column(nullable=false)
	private LocalDateTime timeOfTranscation;
	
	@Column(nullable = false)
	private long amount;
	

}
