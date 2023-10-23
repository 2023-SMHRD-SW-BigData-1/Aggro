package com.smhrd.bigdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.smhrd.bigdata.dto.RequestBoardDetail;
import com.smhrd.bigdata.dto.RequestBoardList;
import com.smhrd.bigdata.entity.Agree;
import com.smhrd.bigdata.entity.NoticeBoard;
import com.smhrd.bigdata.service.CommunityService;
import com.smhrd.bigdata.service.JwtTokenService;

@RestController
@RequestMapping("/community") // 클래스 레벨에서 "/community"를 정의
public class CommunityController {

	@Autowired
	JwtTokenService tokenService;

	@Autowired
	CommunityService communityService;

	Gson gson = new Gson();

	// 게시글 페이지
	@GetMapping("/{communityPage}")
	public RequestBoardList community(@PathVariable("communityPage") int communityPage) {

		Pageable pageable = PageRequest.of(communityPage, 10); // 0번째 페이지, 페이지당 10개의 항목

		RequestBoardList data = new RequestBoardList(); // 반환할 틀 생성

		data.setData(communityService.dataList(pageable)); // 데이터 가져와서 세팅하기

		if (communityPage > 10000 || data.getData().size() < 10) { // 10000 페이지까지 보여주기 or 한 페이지에 10개 미만 이라면 다음 페이지 없음
			data.setStatusCode(204); // default가 200이므로 제외
		}

		return data;
	}

	// 글작성
	@PostMapping("/writeProc")
	public ResponseEntity<String> writeProc(@RequestBody NoticeBoard board, // 게시글 정보 가져오기
			@RequestHeader("Authorization") String jwtToken) { // 토근이 저장된 Header 가져오기

		Gson gson = new Gson();

		// 토큰 전처리
		jwtToken = jwtToken.replace("Bearer ", "");
		Boolean result = tokenService.validateJwtToken(jwtToken); // 토큰값 검사, 3시간 유효

		if (!result) { // 토큰값이 유효해야 저장할 수 있음
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("토큰값 만료");
		}

		board.setUserId(tokenService.getUserIdFromToken(jwtToken));

		communityService.write(board);

		return null;
	}

	// 자세한 글 보기
	@GetMapping("/detail/{noticeSeq}")
	public RequestBoardDetail detailBoard(@PathVariable("noticeSeq") Long noticeSeq) {

		RequestBoardDetail boardDetail = new RequestBoardDetail();

		boardDetail.setData(communityService.findOneByNoticeSeq(noticeSeq));

		if (boardDetail.getData() == null) {
			boardDetail.setStatusCode(400);
		}

		return boardDetail;
	}

	// 조회수 업데이트
	@PutMapping("/update/view/{noticeSeq}")
	public void updateViewCount(@PathVariable("noticeSeq") Long noticeSeq,
			@RequestHeader("Authorization") String jwtToken) {
		// 토큰 전처리
		jwtToken = jwtToken.replace("Bearer ", "");
		Boolean result = tokenService.validateJwtToken(jwtToken); // 토큰값 검사, 3시간 유효

		communityService.updateCommunityHits(noticeSeq);

	}

	// 게시글 삭제
	@DeleteMapping("/delete/{noticeSeq}")
	public ResponseEntity<String> delete(@PathVariable("noticeSeq") Long noticeSeq,
			@RequestHeader("Authorization") String jwtToken) {
		// 토큰 전처리
		jwtToken = jwtToken.replace("Bearer ", "");
		Boolean result = tokenService.validateJwtToken(jwtToken); // 토큰값 검사, 3시간 유효

		if (!result) { // 토큰값이 유효해야 저장할 수 있음
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("토큰값 만료");

		}
		communityService.deleteNoticeBoard(noticeSeq);

		return null;
	}

	// 게시글 수정
	@PutMapping("/update")
	public ResponseEntity<String> updateBoard(@RequestBody NoticeBoard board,
			@RequestHeader("Authorization") String jwtToken) {

		// 토큰 전처리
		jwtToken = jwtToken.replace("Bearer ", "");
		Boolean result = tokenService.validateJwtToken(jwtToken); // 토큰값 검사, 3시간 유효

		if (!result) { // 토큰값이 유효해야 저장할 수 있음
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("토큰값 만료");
		}

		board.setUserId(tokenService.getUserIdFromToken(jwtToken));

		communityService.updateNoticeBoard(board);

		return null;

	}

	// 게시글 추천
	@PutMapping("/update/like/{noticeSeq}")
	public ResponseEntity<RequestBoardDetail> updateAgree(@RequestBody Agree agree,
			@PathVariable("noticeSeq") Long noticeSeq, @RequestHeader("Authorization") String jwtToken) {

		// 토큰 전처리
		jwtToken = jwtToken.replace("Bearer ", "");
		Boolean result = tokenService.validateJwtToken(jwtToken); // 토큰값 검사, 3시간 유효

		if (!result) { // 토큰값이 유효해야 저장할 수 있음
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detailBoard(noticeSeq));
		}

		communityService.saveAgree(agree, noticeSeq);

		return ResponseEntity.status(HttpStatus.OK).body(detailBoard(noticeSeq));
	}

	// 게시글 검색
	@GetMapping("/find/{inputValue}")
	public RequestBoardList searchBoard(@PathVariable("inputValue") String inputValue,
			@RequestParam("searchOption") String searchOption) {

		Pageable pageable = PageRequest.of(0, 10); // 0번째 페이지, 페이지당 10개의 항목

		inputValue = inputValue.replace("04846", ""); // "0486"을 ""로 치환

		RequestBoardList data = new RequestBoardList(); // 반환할 틀 생성

		if (inputValue.equals("")) {
			data.setData(communityService.dataList(pageable));
		} else {
			data.setData(communityService.searchBoardList(inputValue, pageable, searchOption)); // 데이터 가져와서 세팅하기
		}

		if (data.getData().size() < 10) { // 10000 페이지까지 보여주기 or 한 페이지에 10개 미만 이라면 다음 페이지 없음
			data.setStatusCode(204); // default가 200이므로 제외
		}

		return data;
	}

}
