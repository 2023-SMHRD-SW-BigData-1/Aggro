package com.smhrd.bigdata.entity;

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
	private String userId;

	@Column(length = 100, nullable = false)
	private String userPw;

	@Column(length = 100, nullable = false)
	private String userNick;

	@Column(length = 100, nullable = false)
	private String userClass;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false)
	private Date joinAt;

}
