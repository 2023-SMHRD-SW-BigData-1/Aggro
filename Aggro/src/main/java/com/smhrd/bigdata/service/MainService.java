package com.smhrd.bigdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.smhrd.bigdata.entity.UserInfo;
import com.smhrd.bigdata.repository.MainRepository;

@Service
public class MainService {

	@Autowired
	MainRepository repository;

	@Autowired
	JwtTokenService tokenService;

	// 회원가입
	public void join(UserInfo userInfo) {

		repository.save(userInfo);

	}

	// 아이디 중복체크
	public boolean idCheck(String userId) {

		UserInfo info = repository.findOneByUserId(userId);

		if (info == null) {
			return false;
		}

		return true;
	}

	public JsonObject login(UserInfo userInfo) {
		UserInfo result = repository.findOneByUserIdAndUserPw(userInfo.getUserId(), userInfo.getUserPw());

		if (result != null) {
			// 조건에 맞는 결과가 있을 때의 처리
			String jwtToken = tokenService.createJwtToken(result.getUserId());

			JsonObject data = new JsonObject();
			data.addProperty("userId", result.getUserId());
			data.addProperty("userNick", result.getUserNick());
			data.addProperty("jwtToken", jwtToken);

			return data;
		}

		return null;
	}

}
