package com.springboot.blog.payload;

import java.time.LocalDateTime;

import com.springboot.blog.entity.Account;
import com.springboot.blog.enums.TranscationType;

import lombok.Data;

@Data
public class TranscationDto {
	private int transctionId;
	private Account account;
	private TranscationType transcationType;
	private LocalDateTime timeOfTranscation;
	private long amount;
}
