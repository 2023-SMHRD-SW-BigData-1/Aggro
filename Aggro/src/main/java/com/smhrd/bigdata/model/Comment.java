package com.smhrd.bigdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Table
@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long comment_seq;
	
	@ManyToOne
	@JoinColumn(name = "notice_seq")
	private NoticeBoard notice_seq;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserInfo user_id;
	
	@Column(length = 100, nullable = false)
	private String details;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date comment_at;
}
