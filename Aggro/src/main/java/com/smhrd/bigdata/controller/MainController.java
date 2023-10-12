package com.smhrd.bigdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public void test() {
		
		System.out.println("서버 접속함");
		
	}
}
