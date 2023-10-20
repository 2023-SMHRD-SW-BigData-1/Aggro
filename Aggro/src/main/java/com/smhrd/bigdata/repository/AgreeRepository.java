package com.smhrd.bigdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.bigdata.entity.Agree;
import com.smhrd.bigdata.entity.NoticeBoard;

@Repository
public interface AgreeRepository extends JpaRepository<Agree, Long> {

	int countAllByNoticeSeq(NoticeBoard noticeSeq);
}
