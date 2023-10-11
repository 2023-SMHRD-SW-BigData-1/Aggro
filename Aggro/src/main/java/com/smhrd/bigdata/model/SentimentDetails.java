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
public class SentimentDetails {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long sentiment_seq;
	
	@OneToOne
	@JoinColumn(name="crawl_seq",referencedColumnName = "crawl_seq")
	private CrawlSite crawl_seq;
	
	@Column(length = 4000, nullable = false)
	private String sent_details;
	
	@Column(length = 100, nullable = false)
	private String sentiment;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date sentiment_at;
	
	
}
