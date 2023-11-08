package com.smhrd.bigdata.entity;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long crawlSeq;

	@Column(length = 100)
	private String mainCategory; // 크롤링 사이트 대분류

	@Column(length = 100)
	private String subCategory; // 크롤링 사이트 소분류

	@Column(length = 1000, nullable = false, unique = true)
	private String crawlUrl;
	
	@Column(length = 4000)
	private String crawlTitle;
	
	@Column(length = 400, nullable = false)
	private int crawlViewCount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
	private Date crawlAt;

}
