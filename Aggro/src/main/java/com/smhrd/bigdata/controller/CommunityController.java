package com.smhrd.bigdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smhrd.bigdata.model.NoticeBoard;
import com.smhrd.bigdata.service.CommunityService;
import com.smhrd.bigdata.service.JwtTokenService;

@RestController
@RequestMapping("/community") // 클래스 레벨에서 "/community"를 정의
public class CommunityController {

	@Autowired
	JwtTokenService tokenService;

	@Autowired
	CommunityService communityService;

	JsonObject jsonObj = new JsonObject();

	// 게시글 페이지
	@GetMapping("/{communityPage}")
	public String community(@PathVariable("communityPage") int communityPage) {

		Pageable pageable = PageRequest.of(communityPage, 10); // 0번째 페이지, 페이지당 10개의 항목
		Page<NoticeBoard> boardPage = communityService.boardList(pageable); // 페이지화 해서 데이터 가져오기
		List<NoticeBoard> boardList = boardPage.getContent(); // 가져온 데이이터에서 실제 내용만 가져오기

		JsonArray dataList = communityService.dataList(boardList);

		int statusCode = 204;

		if (boardList.size() > 0) {
			statusCode = 200;
		}

		// --------------------------------------------------

		jsonObj.add("data", dataList);

		jsonObj.addProperty("statusCode", statusCode); // statusCode 추가

		return jsonObj.toString();
	}

	// 글작성
	@PostMapping("/writeProc")
	public ResponseEntity<String> writeProc(@RequestBody NoticeBoard board, // 게시글 정보 가져오기
			@RequestHeader("Authorization") String jwtToken) { // 토근이 저장된 Header 가져오기

		// 토큰 전처리
		jwtToken = jwtToken.replace("Bearer ", "");
		Boolean result = tokenService.validateJwtToken(jwtToken); // 토큰값 검사, 1시간 유효

		if (result) { // 토큰값이 유효해야 저장할 수 있음
			communityService.write(board);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("토큰값 만료");
		}

		return null;
	}
	
	// 자세한 글 보기
	@GetMapping("/detail/{noticeSeq}")
	public void detailBoard(@PathVariable("noticeSeq") Long noticeSeq) {
		System.out.println(noticeSeq);
		
		
	}
	
	@PutMapping("/update/view/{noticeSeq}")
	public void update(@PathVariable("noticeSeq") Long noticeSeq, @RequestHeader("Authorization")String jwtToken) {
		// 토큰 전처리
		jwtToken = jwtToken.replace("Bearer ", "");
		Boolean result = tokenService.validateJwtToken(jwtToken); // 토큰값 검사, 1시간 유효
	}

}
