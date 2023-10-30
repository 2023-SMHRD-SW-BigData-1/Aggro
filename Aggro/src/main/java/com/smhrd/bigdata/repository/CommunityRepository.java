package com.smhrd.bigdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smhrd.bigdata.entity.NoticeBoard;

@Repository
public interface CommunityRepository extends JpaRepository<NoticeBoard, Long> {
	NoticeBoard findOneByNoticeSeq(Long noticeSeq);

	@Query("SELECT nb FROM NoticeBoard nb WHERE nb.title LIKE %:text% OR nb.details LIKE %:text%")
	Page<NoticeBoard> findAllByTitleLikeOrDetailsLike(@Param("text") String text, Pageable pageable);

	@Query("SELECT nb FROM NoticeBoard nb JOIN nb.userId ui WHERE ui.userNick LIKE %:userNick%")
	Page<NoticeBoard> findByUserId_UserNick(String userNick, Pageable pageable);

	@Query("SELECT nb FROM NoticeBoard nb WHERE nb.title LIKE %:text%")
	Page<NoticeBoard> findAllByTitleLike(@Param("text") String text, Pageable pageable);
}
