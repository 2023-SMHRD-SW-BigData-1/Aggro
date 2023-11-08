package com.smhrd.bigdata.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.smhrd.bigdata.entity.CrawlSite;
import com.smhrd.bigdata.repository.CrawlSiteRepository;

@Service
public class WebCrawlerService {

	@Autowired
	CrawlSiteRepository crawlSiteRepository;

	@Scheduled(cron = "0/1 * 18-23,0-7 * * *")
	public void bobadreamCrawlAndProcessData() {

		try {

			String url = crawlSiteRepository.findByCrawlUrlLike("boba");

			Document document = Jsoup.connect(url).get();

			String next_url = "https://www.bobaedream.co.kr/view"
					+ document.select("ul.Upnav>li.p2>a").attr("href").split("\\/view")[1];

			document = Jsoup.connect(next_url).get();

			String crawlTitle = document.select("div.writerProfile>dl>dt>strong").text().replaceAll("\\[\\d+\\]", "")
					.trim();
			String countGroupText = document.select("div.writerProfile>dl>dt>span.countGroup").text();
			String crawlUrl = document.select("#copy").text();

			String crawlViewCount = countGroupText.split("\\|")[0].split("\\s")[1].replaceAll("[^0-9]", "");
			String crawlAtStr = countGroupText.split("\\|")[3].split("\\s")[1] + " "
					+ countGroupText.split("\\|")[3].split("\\s")[3];

			Date crawlAt = new SimpleDateFormat("yyyy.MM.dd HH:mm").parse(crawlAtStr);

			CrawlSite crawlSite = new CrawlSite();
			crawlSite.setCrawlUrl(crawlUrl);
			crawlSite.setCrawlTitle(crawlTitle);
			crawlSite.setCrawlUrl(crawlUrl);
			crawlSite.setCrawlViewCount(Integer.parseInt(crawlViewCount));
			crawlSite.setCrawlAt(crawlAt);
			crawlSite.setMainCategory("사회");
			crawlSite.setSubCategory("이슈");

			crawlSiteRepository.save(crawlSite);
		} catch (Exception e) {
			System.err.println(e);
		}

	}

}
