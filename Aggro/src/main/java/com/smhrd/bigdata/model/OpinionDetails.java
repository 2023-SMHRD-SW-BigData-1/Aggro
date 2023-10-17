package com.smhrd.bigdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
public class OpinionDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long opinionSeq;
	
	@OneToOne
	@JoinColumn(name="crawlSeq",referencedColumnName = "crawlSeq")
	private CrawlSite crawlSeq;
	
	@Column(length = 4000, nullable = false)
	private String opinionDetails;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date opinionAt;
	
}
