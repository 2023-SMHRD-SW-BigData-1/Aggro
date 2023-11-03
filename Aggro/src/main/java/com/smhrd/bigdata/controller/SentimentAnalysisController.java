package com.smhrd.bigdata.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.bigdata.entity.SentimentDetails;
import com.smhrd.bigdata.service.DetailService;
import com.smhrd.bigdata.service.SentimentAnalysisService;

@RestController
@RequestMapping("/api")
public class SentimentAnalysisController {

	@Autowired
	private SentimentAnalysisService sentimentAnalysisService;

	@Autowired
	private DetailService detailService;

	@PostMapping("/analyzeSentiment")
	public SentimentDetails analyzeSentiment(@RequestBody Map<String, String> requestBody) {

		if (requestBody.get("content") != null) {
			String content = requestBody.get("content"); // client에서 입력한 값 저장

			// DB에 이미 같은 키워드로 검색된 전적이 있는지 검색하기
			SentimentDetails details = sentimentAnalysisService.sentimentDetails(content);

			if (details == null || details.getSentDetails().equals("")) { // 값이 없을 경우 실행

				if (details == null) {

					// 값이 빈 객체에 새로 객체 생성 후 키워드를 담은 후 DB에 저장
					details = new SentimentDetails();

					details.setSentiment(content);
					details.setSentDetails("");

					sentimentAnalysisService.sentimentDetailsSave(details);
				}

				// DB에 저장된 키를 다시 가져온다
				details = sentimentAnalysisService.sentimentDetails(content);

				// -----------------------------------------------------

				List<String> result = detailService.searchWordTitle(content); //

				List<String> data = new ArrayList<>();

				try {

					for (int i = 0; i < 1000; i++) {
						System.out.println(i + 1 + "회 차 통신 시도");

						String sentiment = sentimentAnalysisService.analyzeSentiment(result.get(i));

						data.add(sentiment);
					}

				} catch (Exception e) {
					System.out.println("에러발생 통신종료");
				}

				finally {
					System.out.println("통신종료");

					// 데이터가 있을 시
					if (data.size() > 0) {

						// 최종 결과물을 백분율 전환 후 DB에 저장
						// 이 후 같은 키워드는 통신을 낮춰 속도를 높이기 위함
						details.setSentDetails(sentimentAnalysisService.sentimentCount(data));

						// 이 후 저장한 details를 client에게 반환
						sentimentAnalysisService.sentimentDetailsSave(details);
					}

				}

			}

			return details;
		}
		return null;

	}

}