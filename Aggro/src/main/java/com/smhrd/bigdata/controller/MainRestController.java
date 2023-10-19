package com.smhrd.bigdata.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.smhrd.bigdata.entity.UserInfo;
import com.smhrd.bigdata.service.JwtTokenService;
import com.smhrd.bigdata.service.MainService;

@RestController
public class MainRestController {

	@Autowired
	MainService mainService;

	@Autowired
	JwtTokenService tokenService;

	JsonObject jsonObj = new JsonObject();

	// 회원가입 로직
	@PostMapping("/join")
	public String join(@RequestBody UserInfo userInfo) {

		boolean check = mainService.idCheck(userInfo.getUserId());

		if (check) {
			jsonObj.addProperty("message", "중복된 아이디 입니다.");
			jsonObj.addProperty("status", false);
		} else {
			mainService.join(userInfo);
			jsonObj.addProperty("status", true);
		}

		// json값을 String 값으로 변환하여 반환

		return jsonObj.toString();
	}

	// 로그인 로직
	@PostMapping("/login")
	public String login(@RequestBody UserInfo userInfo) {

		JsonObject data = mainService.login(userInfo); // 로그인 시도

		if (data != null) {
			jsonObj.addProperty("message", "로그인성공"); // 성공 메시지
		} else {
			jsonObj.addProperty("message", "로그인실패"); // 실패 메시지
		}

		jsonObj.add("data", data); // 실패 했을 경우 null값 반환

		return jsonObj.toString();
	}

	// 간편 로그인 로직
	@PostMapping("/oauth")
	public String oauthLogin(@RequestBody String googleData) throws ParseException {

		JSONParser p = new JSONParser();
		JSONObject obj = (JSONObject) p.parse(googleData); // json 데이터 UserInfo 엔티티 객체 형태로 변환

		// 원하는 데이터 뽑아오기
		String userId = (String) obj.get("userId");
		String userNick = (String) obj.get("userNick");
		String userPw = (String) obj.get("googleId");

		UserInfo userInfo = new UserInfo();

		userInfo.setUserClass("0");
		userInfo.setUserId(userId);
		userInfo.setUserPw(userPw);
		userInfo.setUserNick(userNick);

		// 로그인 시도
		JsonObject data = mainService.login(userInfo);

		if (data == null) {
			mainService.join(userInfo); // 값이 없다면 회원 데이터가 없으므로 회원등록

			data = mainService.login(userInfo); // 그 후 데이터 다시 저장
		}

		jsonObj.addProperty("message", "로그인성공");
		jsonObj.add("data", data);

		return jsonObj.toString();
	}

}
