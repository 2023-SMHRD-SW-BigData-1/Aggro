package com.smhrd.bigdata.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smhrd.bigdata.entity.UserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenService {

	// secret key 설정하기
	private final SecretKey secretKey;
	// 만료일
	private final Long expiredTime = 1000 * 60L * 60L * 3L; // 유효시간 3시간

	// key 설정하기
	public JwtTokenService(@Value("${jwt.secret}") String SECRET_KEY) {

		this.secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}

	// JWT 토큰 생성
	public String createJwtToken(UserInfo userInfo) {

		String token = Jwts.builder().issuedAt(new Date()).subject(userInfo.getUserId()) //
				.claims(createClaims(userInfo)) // 클레임,
				.header().type("JWT") // 타입 설정
				.add("regDate", System.currentTimeMillis()) // 토큰 설정 날짜 추가
				.and() // 토큰에 헤더 추가
				.expiration(new Date(System.currentTimeMillis() + expiredTime)) // 1hour
				.signWith(secretKey).compact();

		return token;
	}

	// JWT 토큰 검증
	public boolean validateJwtToken(String token) {

		try {
			// Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
			Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
			return true; // 서명이 유효한 경우 true 반환
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			System.out.println("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			System.out.println("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			System.out.println("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			System.out.println("JWT 토큰이 잘못되었습니다.");
		}
		return false; // 서명이 유효하지 않은 경우 false 반환
	}

	// 클레임 생성
	// 계정 정보를 담은 객체
	private Map<String, Object> createClaims(UserInfo userInfo) {
		Map<String, Object> claims = new HashMap<>();

		claims.put("userInfo", userInfo);

		return claims;

	}

	// 서명 반환하기
	private Claims getClaims(String token) {

		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
	}

	// token 값 안에 담겨있는 유저정보 가져오기
	public UserInfo getUserIdFromToken(String token) {

		// UserInfo userInfo = (UserInfo) getClaims(token).get("userInfo");

		final ObjectMapper mapper = new ObjectMapper();

		// return userInfo;
		return mapper.convertValue(getClaims(token).get("userInfo"), UserInfo.class);
	}

}
