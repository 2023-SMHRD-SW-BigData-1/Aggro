package com.smhrd.bigdata.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.bigdata.entity.CrawlSite;
import com.smhrd.bigdata.service.DetailService;

@RestController
@RequestMapping("/ranking/")
public class DetailRestController {

	@Autowired
	DetailService detailService;

	@GetMapping("/{searchWord}")
	public List<CrawlSite> searchWordRanking(@PathParam("searchWord") String searchWord) {

		return detailService.searchWordRanking(searchWord);

	}
}
