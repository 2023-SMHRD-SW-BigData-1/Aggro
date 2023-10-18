package com.smhrd.bigdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.bigdata.model.NoticeBoard;

@Repository
public interface CommunityRepository extends JpaRepository<NoticeBoard, String> {

}
