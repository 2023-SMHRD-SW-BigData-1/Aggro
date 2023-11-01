package com.smhrd.bigdata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smhrd.bigdata.entity.CrawlSite;
import com.smhrd.bigdata.repository.CrawlSiteRepository;

@Service
public class DetailService {
	
	@Autowired
	CrawlSiteRepository crawlSiteRepository;
	
	public List<CrawlSite> searchWordRanking(String text) {
		return crawlSiteRepository.findAllByCrawlTitleLikeOrCrawlContent(text);
	}
	
	public List<String> searchWordTitle(String text){
		
		return crawlSiteRepository.findCrawlTitleByCrawlTitleLikeOrCrawlContent(text);
	}
}
