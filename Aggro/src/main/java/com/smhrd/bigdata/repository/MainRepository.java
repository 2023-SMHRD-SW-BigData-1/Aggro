package com.smhrd.bigdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smhrd.bigdata.entity.UserInfo;

@Repository
public interface MainRepository extends JpaRepository<UserInfo, String> {

	UserInfo findOneByUserId(String userId);

	UserInfo findOneByUserIdAndUserPw(String userId, String userPw);
	
	boolean existsByUserId(String userId);

}
