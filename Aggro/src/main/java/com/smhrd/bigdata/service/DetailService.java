package com.smhrd.bigdata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.bigdata.entity.CrawlSite;
import com.smhrd.bigdata.entity.OpinionDetails;
import com.smhrd.bigdata.repository.CrawlSiteRepository;
import com.smhrd.bigdata.repository.OpinionDetailsRepository;

@Service
public class DetailService {
	
	@Autowired
	CrawlSiteRepository crawlSiteRepository;
	
	@Autowired
	OpinionDetailsRepository opinionDetailsRepository;
	
	public List<CrawlSite> searchWordRanking(String text) {
		return crawlSiteRepository.findAllByCrawlTitleLikeOrCrawlContent(text);
	}
	
	public List<String> searchWordTitle(String text){
		
		return crawlSiteRepository.findCrawlTitleByCrawlTitleLikeOrCrawlContent(text);
	}
	
	public OpinionDetails searchWordOpinion(String text) {
		
		return opinionDetailsRepository.findAllByOpinion(text);
	}
}
