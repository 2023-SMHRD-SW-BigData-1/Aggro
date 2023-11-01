package com.smhrd.bigdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smhrd.bigdata.entity.CrawlSite;

public interface CrawlSiteRepository extends JpaRepository<CrawlSite, Long> {

	@Query("SELECT cs FROM CrawlSite cs where cs.crawlTitle LIKE %:text% OR cs.crawlContent LIKE %:text%")
	List<CrawlSite> findAllByCrawlTitleLikeOrCrawlContent(@Param("text") String text);
	
	
	@Query("SELECT cs.crawlTitle FROM CrawlSite cs where cs.crawlTitle LIKE %:text% OR cs.crawlContent LIKE %:text%")
	List<String> findCrawlTitleByCrawlTitleLikeOrCrawlContent(@Param("text") String text);

}
