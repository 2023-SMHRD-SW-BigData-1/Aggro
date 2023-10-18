package com.smhrd.bigdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.bigdata.model.Comment;
import com.smhrd.bigdata.model.NoticeBoard;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

	List<Comment> findAllByNoticeSeq(NoticeBoard noticeSeq);

}
