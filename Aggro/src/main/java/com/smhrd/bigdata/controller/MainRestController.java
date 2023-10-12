package com.smhrd.bigdata.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainRestController {

	@GetMapping("/test")
	public String axiosTest() {
		
		System.out.println("서버 접속함");
		
		return "통신됨";
	}
}
