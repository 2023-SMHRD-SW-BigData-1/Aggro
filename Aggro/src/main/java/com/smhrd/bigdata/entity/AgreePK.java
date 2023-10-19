package com.smhrd.bigdata.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class AgreePK implements Serializable{
	
	@ManyToOne
	@JoinColumn(name="noticeSeq")
	private NoticeBoard noticeSeq;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private UserInfo userId;
	
}
