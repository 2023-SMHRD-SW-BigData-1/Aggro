package com.smhrd.bigdata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smhrd.bigdata.model.Comment;
import com.smhrd.bigdata.model.NoticeBoard;
import com.smhrd.bigdata.repository.CommentRepository;
import com.smhrd.bigdata.repository.CommunityRepository;

@Service
public class CommunityService {

	@Autowired
	CommunityRepository communityRepository;

	@Autowired
	CommentRepository commentRepository;

	public void write(NoticeBoard board) {

		communityRepository.save(board);

	}

	// 게시글 리스트 가져오기
	public Page<NoticeBoard> boardList(Pageable pageable) {

		// Sort.by()를 사용하여 'id' 필드를 역순으로 정렬합니다. 이 예에서는 ID를 기준으로 역순 정렬하도록 설정했습니다.
		Sort sort = Sort.by(Sort.Direction.DESC, "noticeSeq");
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

		return communityRepository.findAll(sortedPageable);
	}

	// 자세한 list 작성하기
	public JsonArray dataList(List<NoticeBoard> boardList) {

		JsonArray dataList = new JsonArray();
		Gson gson = new Gson();
		JsonObject dto = new JsonObject(); // 베열 하나에 들어갈 값

		dto.addProperty("type", 0);
		// post key 부분
		JsonObject post = new JsonObject();
		post.addProperty("noticeSeq", "");
		post.addProperty("title", "");
		post.addProperty("noticeAt", "");
		// userId key 부분
		JsonObject userIdObject = new JsonObject();
		userIdObject.addProperty("userNick", "");
		post.add("userId", gson.toJsonTree(userIdObject));

		post.add("replies", gson.toJsonTree(new JsonArray()));

		dto.add("post", post);

		for (NoticeBoard board : boardList) {

			dto = new JsonObject();

			dto.addProperty("type", 1);

			post = new JsonObject();
			post.addProperty("noticeSeq", board.getNoticeSeq());
			post.addProperty("title", board.getTitle());
			post.addProperty("noticeAt", board.getNoticeAt().toString());
			post.add("userId", gson.toJsonTree(board.getUserId()));

			List<Comment> commentList = commentList(board);

			post.add("replies", gson.toJsonTree(commentList));

			dto.add("post", post);

		}

		dataList.add(dto);

		return dataList;
	}

	// 댓글 전부 가져오기
	public List<Comment> commentList(NoticeBoard noticeSeq) {

		return commentRepository.findAllByNoticeSeq(noticeSeq);

	}
	
	// 게시글 하나 검색하기
	public void findOneByNoticeSeq() {
		
	}
}
