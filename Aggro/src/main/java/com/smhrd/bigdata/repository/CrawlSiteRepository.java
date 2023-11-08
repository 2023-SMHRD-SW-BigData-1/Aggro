package com.smhrd.bigdata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smhrd.bigdata.entity.CrawlSite;

public interface CrawlSiteRepository extends JpaRepository<CrawlSite, Long> {

	@Query("SELECT cs FROM CrawlSite cs where cs.crawlTitle LIKE %:text%")
	List<CrawlSite> findAllByCrawlTitleLike(@Param("text") String text);

	@Query("SELECT cs.crawlTitle FROM CrawlSite cs where cs.crawlTitle LIKE %:text%")
	List<String> findCrawlTitleByCrawlTitleLike(@Param("text") String text);

    @Query(value = "SELECT cs.crawl_url FROM crawl_site cs WHERE cs.crawl_url LIKE %:text% ORDER BY cs.crawl_seq DESC LIMIT 1", nativeQuery = true)
    String findByCrawlUrlLike(@Param("text") String text);

}
