package com.smhrd.bigdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smhrd.bigdata.entity.CommunityHits;
import com.smhrd.bigdata.entity.NoticeBoard;

public interface CommunityHitsRepository extends JpaRepository<CommunityHits, Long> {
	
	int countAllByNoticeSeq(NoticeBoard noticeSeq);
}
