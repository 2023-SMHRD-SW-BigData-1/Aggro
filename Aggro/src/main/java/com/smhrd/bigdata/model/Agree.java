package com.smhrd.bigdata.model;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
public class Agree {
	
	@EmbeddedId
	private AgreePK agreePK;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date agreeAt;

}
