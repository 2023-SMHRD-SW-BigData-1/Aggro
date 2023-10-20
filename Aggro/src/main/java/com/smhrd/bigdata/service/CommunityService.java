package com.smhrd.bigdata.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smhrd.bigdata.dto.CommunityDto;
import com.smhrd.bigdata.dto.Post;
import com.smhrd.bigdata.entity.Agree;
import com.smhrd.bigdata.entity.AgreePK;
import com.smhrd.bigdata.entity.Comment;
import com.smhrd.bigdata.entity.CommunityHits;
import com.smhrd.bigdata.entity.NoticeBoard;
import com.smhrd.bigdata.repository.AgreeRepository;
import com.smhrd.bigdata.repository.CommentRepository;
import com.smhrd.bigdata.repository.CommunityHitsRepository;
import com.smhrd.bigdata.repository.CommunityRepository;

@Service
public class CommunityService {

	@Autowired
	CommunityRepository communityRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	AgreeRepository agreeRepository;

	@Autowired
	CommunityHitsRepository communityHitsRepository;

	public void write(NoticeBoard board) {

		communityRepository.save(board);

	}

	// 자세한 리스트 작성하기
	public List<Object> dataList(Pageable pageable) {
		// Sort.by()를 사용하여 'id' 필드를 역순으로 정렬합니다. 이 예에서는 ID를 기준으로 역순 정렬하도록 설정했습니다.
		Sort sort = Sort.by(Sort.Direction.DESC, "noticeSeq");
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

		Page<NoticeBoard> boardPage = communityRepository.findAll(sortedPageable); // 페이지화 해서 데이터 가져오기

		List<Object> dataList = new ArrayList<>(); // 빈 틀을 만들기 위한 Json 배열

		// 가져온 게시글들을 하나씩 담는 과정
		for (NoticeBoard board : boardPage) {
			// JsonObject dto = new JsonObject();
			CommunityDto dto = new CommunityDto();
			// dto.addProperty("type", 1);
			dto.setType(1);

			Post post = new Post();
			post.setBoard(board);
			post.setReplies(commentRepository.findAllByNoticeSeq(board));

			dto.setPost(post);

			dataList.add(dto);
		}

		return dataList;
	}

	// 댓글 전부 가져오기
	public List<Comment> commentList(NoticeBoard noticeSeq) {

		return commentRepository.findAllByNoticeSeq(noticeSeq);

	}

	// 게시글 하나 검색하기
	public CommunityDto findOneByNoticeSeq(Long noticeSeq) {
		NoticeBoard board = communityRepository.findOneByNoticeSeq(noticeSeq);

		if (board != null) {

			CommunityDto dto = new CommunityDto(); // 모든 것을 담을 공간
			dto.setType(1); // dto 세팅

			Post post = new Post(); // 게시글 담을 공간

			post.setBoard(board); // 게시글 정보 담기
			post.setReplies(commentRepository.findAllByNoticeSeq(board)); // 댓글 정보 담기

			// 추천수 확인을 위한 데이터 세팅하기
			AgreePK agreePK = new AgreePK();

			agreePK.setNoticeSeq(post);
			agreePK.setUserId(post.getUserId());

			// 추천수 데이터 가져오기
			post.setLikeCount(agreeRepository.countAllByAgreePK(agreePK));

			// 조회수 가져오기
			post.setViewCount(communityHitsRepository.countAllByNoticeSeq(board));

			dto.setPost(post);

			return dto;
		}

		return null;
	}

	// 조회수 업데이트
	public void updateCommunityHits(Long noticeSeq) {

		// 게시글 번호 저장
		NoticeBoard board = new NoticeBoard();
		board.setNoticeSeq(noticeSeq);

		CommunityHits hits = new CommunityHits();

		hits.setNoticeSeq(board);

		communityHitsRepository.save(hits);
	}

	// 게시글 삭제
	public void deleteNoticeBoard(Long noticeSeq) {
		communityRepository.deleteById(noticeSeq);
	}

	// 게시글 수정
	public void updateNoticeBoard(NoticeBoard board) {
		communityRepository.save(board);
	}

	// 댓글 작성
	public void saveCommentWrite(Comment comment) {
		commentRepository.save(comment);
	}

	// 댓글 삭제
	public void deleteComment(Long commentSeq) {
		commentRepository.deleteById(commentSeq);
	}

	// 추천 누르기
	public void saveAgree(AgreePK agreePK, Long noticeSeq) {

		NoticeBoard board = new NoticeBoard();

		board.setNoticeSeq(noticeSeq);
		agreePK.setNoticeSeq(board);

		Agree agree = new Agree();

		agree.setAgreePK(agreePK);

		agreeRepository.save(agree);

	}

	// 게시글 검색하기
	public List<Object> searchBoardList(String inputValue, Pageable pageable, String searchOption) {

		Sort sort = Sort.by(Sort.Direction.DESC, "noticeSeq");
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

		// titleAndDetails default

		Page<NoticeBoard> boardPage = null;

		switch (searchOption) {
		case "writer":
			boardPage = communityRepository.findByUserId_UserNick(inputValue, sortedPageable);
			break;

		case "titleAndDetails":
			boardPage = communityRepository.findAllByTitleLikeOrDetailsLike(inputValue, sortedPageable);
			break;
		
		default:
			boardPage = communityRepository.findAllByTitleLike(inputValue, sortedPageable);
			break;
		}

		List<Object> dataList = new ArrayList<>(); // 빈 틀을 만들기 위한 Json 배열

		// 가져온 게시글들을 하나씩 담는 과정
		for (NoticeBoard board : boardPage) {
			// JsonObject dto = new JsonObject();
			CommunityDto dto = new CommunityDto();
			// dto.addProperty("type", 1);
			dto.setType(1);

			Post post = new Post();
			post.setBoard(board);
			post.setReplies(commentRepository.findAllByNoticeSeq(board));

			dto.setPost(post);

			dataList.add(dto);
		}

		return dataList;

	}
}
