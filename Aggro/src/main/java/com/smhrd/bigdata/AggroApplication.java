package com.smhrd.bigdata;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class AggroApplication {

	public static void main(String[] args) {
		SpringApplication.run(AggroApplication.class, args);
	}

	// CORS 처리
	// 리액트 페이지 URL 예외처리
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods(HttpMethod.GET.name(),
						HttpMethod.HEAD.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
						HttpMethod.DELETE.name());
				;
			}
		};
	}

//	// 초(0-59), 분(0-59), 시간(0-23), 일(1-31), 월(1-12), 요일(0-6)
//	// (0: 일, 1: 월, 2:화, 3:수, 4:목, 5:금, 6:토)
//	@Scheduled(cron = "0 0 4 * * *", zone = "Asia/Seoul")
//	public void crawlingRun() {
//		System.out.println("새벽 4시마다 나타남");
//	}
//
//	@Scheduled(cron = "0 0/5 * * * *", zone = "Asia/Seoul")
//	public void crawlTest() {
//		System.out.println(new Date());
//	}
//
//	@Scheduled(fixedDelay = 5000)
//	public void test() {
//
//	}

	/*
	 * // 1시간 마다 실행 ex) 01:00, 02:00, 03:00 ...
	 * 
	 * @Scheduled(cron = "0 0 0/1 * * *") public void run() {    
	 * System.out.println("Hello CoCo World!"); }
	 * 
	 * // 매일 9시00분-9시55분, 18시00분-18시55분 사이에 5분 간격으로 실행
	 * 
	 * @Scheduled(cron = "0 0/5 9,18 * * *") public void run() {
	 *     System.out.println("Hello CoCo World!"); }
	 */

}
