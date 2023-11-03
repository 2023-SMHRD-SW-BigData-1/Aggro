package com.smhrd.bigdata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.bigdata.entity.CrawlSite;
import com.smhrd.bigdata.entity.OpinionDetails;
import com.smhrd.bigdata.service.DetailService;

@RestController
@RequestMapping("/ranking/")
public class DetailRestController {

	@Autowired
	DetailService detailService;

	@GetMapping(value = { "/detail/{searchWord}", "/detail" })
	public List<CrawlSite> searchWordRanking(@PathVariable(name = "searchWord", required = false) String searchWord) {

		if (searchWord == null && !(searchWord.equals("undefined"))) {
			searchWord = "";
		}

		return detailService.searchWordRanking(searchWord);

	}

	@GetMapping("/analyOpinion/{searchWord}")
	public String opinionDetail(@PathVariable(name = "searchWord", required = false) String searchWord) {

		if (!(searchWord.equals("undefined"))) {

			OpinionDetails opinionDetails = detailService.searchWordOpinion(searchWord);

			return opinionDetails.getOpinionDetails();
		}

		return null;

	}
}
