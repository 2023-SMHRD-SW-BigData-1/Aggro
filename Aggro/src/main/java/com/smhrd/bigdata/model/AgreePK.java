package com.smhrd.bigdata.model;

import java.io.Serializable;

import javax.persistence.JoinColumn;

import lombok.Data;

@Data
public class AgreePK implements Serializable{
	
	
	@JoinColumn(name = "notice_seq")
	private NoticeBoard notice_seq;
	
	@JoinColumn(name = "user_id")
	private UserInfo user_id;
	
}
