package com.smhrd.bigdata.controller;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.bigdata.model.UserInfo;

@RestController
public class MainRestController {

	@GetMapping("/test")
	public String axiosTest() {
		
		System.out.println("서버 접속함");
		
		return "통신됨";
	}
	
	@PostMapping("/form")
	@ResponseBody
	public String axiosForm(@RequestBody UserInfo userInfo) {
		
		System.out.println("데이터 통신 연결됨");
		System.out.println(userInfo.getUser_id());
		System.out.println(userInfo.getUser_pw());
		System.out.println(userInfo.getUser_nick());
		
		
		return "데이터 넘어옴";
	}
}
