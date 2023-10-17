package com.smhrd.bigdata.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenService {

	// JWT 비밀키
	private final String SECRET_KEY = "secretsecretsecretsecretsecretsecretsecretsecretsecret";

	// JWT 토큰 생성
	public String createJwtToken(String subject) {

		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

		String token = Jwts.builder()
				.issuedAt(new Date())
				.subject(subject)
				.expiration(new Date(System.currentTimeMillis() + 3600000)) // 1hour
				.signWith(secretKey)
				.compact();

		return token;
	}

	// JWT 토큰 검증
	public boolean validateJwtToken(String token) {

		SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

		try {
			// Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
			Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token);
			return true; // 서명이 유효한 경우 true 반환
		} catch (Exception e) {
			// TODO: handle exception
			return false; // 서명이 유효하지 않은 경우 false 반환
		}
	}
	
}
