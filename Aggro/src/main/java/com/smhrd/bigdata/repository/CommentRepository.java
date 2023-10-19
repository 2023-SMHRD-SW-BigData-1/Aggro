package com.smhrd.bigdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.bigdata.entity.Comment;
import com.smhrd.bigdata.entity.NoticeBoard;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByNoticeSeq(NoticeBoard noticeSeq);

}
