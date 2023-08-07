package com.springboot.blog.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AccountOverViewService {
	ResponseEntity<String> accountOverview(String username);

}
