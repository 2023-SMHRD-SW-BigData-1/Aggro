package com.smhrd.bigdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.smhrd.bigdata.entity.Comment;
import com.smhrd.bigdata.service.CommunityService;
import com.smhrd.bigdata.service.JwtTokenService;

@RestController
@RequestMapping("/reply")
public class CommentController {
	@Autowired
	JwtTokenService tokenService;

	@Autowired
	CommunityService communityService;

	@PostMapping("/write")
	public ResponseEntity<String> commentWrite(@RequestBody Comment comment,
			@RequestHeader("Authorization") String jwtToken) {

		// 토큰 전처리
		jwtToken = jwtToken.replace("Bearer ", "");
		Boolean result = tokenService.validateJwtToken(jwtToken); // 토큰값 검사, 1시간 유효

		if (!result) { // 토큰값이 유효해야 저장할 수 있음
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("토큰값 만료");
		}

		Gson gson = new Gson();

		System.out.println(gson.toJsonTree(comment));

		communityService.saveCommentWrite(comment);

		return null;
	}

	@DeleteMapping("/delete/{commentSeq}")
	public ResponseEntity<String> commentDelte(@PathVariable("commentSeq") Long commentSeq,
			@RequestHeader("Authorization") String jwtToken) {

		// 토큰 전처리
		jwtToken = jwtToken.replace("Bearer ", "");
		Boolean result = tokenService.validateJwtToken(jwtToken); // 토큰값 검사, 1시간 유효

		if (!result) { // 토큰값이 유효해야 저장할 수 있음
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("토큰값 만료");
		}

		communityService.deleteComment(commentSeq);

		return null;
	}
}
