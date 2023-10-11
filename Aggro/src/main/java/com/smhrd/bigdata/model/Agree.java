package com.smhrd.bigdata.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@IdClass(AgreePK.class)
public class Agree {
	
	@Id
	private Long notice_seq;
	
	@Id
	private String user_id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date agree_at;

}
