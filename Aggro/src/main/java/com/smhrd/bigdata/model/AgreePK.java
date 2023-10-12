package com.smhrd.bigdata.model;

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
	@JoinColumn(name="notice_seq")
	private NoticeBoard notice_seq;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserInfo user_id;
	
}
