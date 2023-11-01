package com.smhrd.bigdata.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.smhrd.bigdata.entity.SentimentDetails;
import com.smhrd.bigdata.repository.SentimentRepository;

@Service
public class SentimentAnalysisService {

	@Value("${naver.api.key.id}")
	private String apiKeyId; // Naver API 사용을 위한 API Key ID

	@Value("${naver.api.key.secret}")
	private String apiKeySecret; // Naver API 사용을 위한 API Key Secret

	@Value("${naver.api.url}")
	private String apiUrl; // Naver API 엔드포인트 URL

	private RestTemplate restTemplate;

	@Autowired
	SentimentRepository sentimentRepository;

	public SentimentAnalysisService() {
		this.restTemplate = new RestTemplate(); // RestTemplate 초기화
	}

	public String analyzeSentiment(String content) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("X-NCP-APIGW-API-KEY-ID", apiKeyId); // API Key ID를 요청 헤더에 설정
		headers.set("X-NCP-APIGW-API-KEY", apiKeySecret); // API Key Secret을 요청 헤더에 설정

		Map<String, String> request = new HashMap<>();
		request.put("content", content); // 요청 본문에 분석할 내용(content) 설정

		HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers); // HTTP 요청 엔터티 생성

		ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class); // API
																														// 받음
		Gson gson = new Gson();
		JsonObject jsonObject = JsonParser.parseString(responseEntity.getBody()).getAsJsonObject();

		// "sentiment" 키의 값을 추출
		String sentiment = jsonObject.get("document").getAsJsonObject().get("sentiment").getAsString();

		return sentiment; // 응답 바디를 반환
	}

	public String sentimentCount(List<String> data) {
		int total = data.size();

		// negative, neutral, positive의 개수를 저장할 변수 초기화
		int negativeCount = 0;
		int neutralCount = 0;
		int positiveCount = 0;

		for (String sentiment : data) {
			if ("negative".equals(sentiment)) {
				negativeCount++;
			} else if ("neutral".equals(sentiment)) {
				neutralCount++;
			} else if ("positive".equals(sentiment)) {
				positiveCount++;
			}
		}

		// 백분율 계산
		double negativePercentage = (double) negativeCount / total * 100;
		double neutralPercentage = (double) neutralCount / total * 100;
		double positivePercentage = (double) positiveCount / total * 100;

		JsonObject percentages = new JsonObject();
		percentages.addProperty("negative", negativeCount / (double) total * 100);
		percentages.addProperty("neutral", neutralCount / (double) total * 100);
		percentages.addProperty("positive", positiveCount / (double) total * 100);

		System.out.println("Negative 백분율: " + negativePercentage + "%");
		System.out.println("Neutral 백분율: " + neutralPercentage + "%");
		System.out.println("Positive 백분율: " + positivePercentage + "%");

		return percentages.toString();
	}

	public SentimentDetails sentimentDetails(String context) {

		return sentimentRepository.findAllBySentiment(context);
	}

	public void sentimentDetailsSave(SentimentDetails sentimentDetails) {

		sentimentRepository.save(sentimentDetails);

	}

}
