package com.smhrd.bigdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class CrawlSite {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long crawlSeq;
	
	@Column(length = 100, nullable = false)
	private String classA;
	
	@Column(length = 100, nullable = false)
	private String classB;
	
	@Column(length = 300, nullable = false)
	private String crawlUrl;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date crawlAt;
}
