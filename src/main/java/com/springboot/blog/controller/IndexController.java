package com.springboot.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

	@PostMapping("/")
	public String index() {
		return "index";
	}

}
