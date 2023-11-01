package com.smhrd.bigdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smhrd.bigdata.entity.SentimentDetails;

public interface SentimentRepository extends JpaRepository<SentimentDetails, Long> {

	SentimentDetails findAllBySentiment(String sentiment);

}
