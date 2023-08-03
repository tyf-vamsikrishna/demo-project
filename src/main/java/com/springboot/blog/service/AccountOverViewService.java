package com.springboot.blog.service;

import org.springframework.http.ResponseEntity;

public interface AccountOverViewService {
	ResponseEntity<String> accountOverview(String username);

}
