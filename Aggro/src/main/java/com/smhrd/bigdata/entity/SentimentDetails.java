package com.smhrd.bigdata.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sentimentSeq;

	@OneToOne
	@JoinColumn(name = "crawlSeq", referencedColumnName = "crawlSeq", foreignKey = @ForeignKey(name = "fk_crawl_site_to_sentiment_details"))
	private CrawlSite crawlSeq;

	@Column(length = 4000, nullable = false)
	private String sentDetails;

	@Column(length = 100, nullable = false)
	private String sentiment;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false, insertable = false)
	private Date sentimentAt;

}
