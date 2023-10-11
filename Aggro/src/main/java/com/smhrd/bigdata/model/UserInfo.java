package com.smhrd.bigdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
public class UserInfo {
	
	@Id
	@Column(length = 100)
	private String user_id;
	
	@Column(length = 100, nullable = false)
	private String user_pw;
	
	@Column(length = 100, nullable = false)
	private String user_nick;
	
	@Column(length = 100, nullable = false)
	private String user_class;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date join_at;
	
}
