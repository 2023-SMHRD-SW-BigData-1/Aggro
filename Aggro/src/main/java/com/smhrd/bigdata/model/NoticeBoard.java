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
public class NoticeBoard {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long notice_seq;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserInfo user_id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date notice_at;
	
	@Column(length = 100, nullable = false)
	private String title;
	
	@Column(length = 4000, nullable = false)
	private String details;
}
