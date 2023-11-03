package com.smhrd.bigdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smhrd.bigdata.entity.OpinionDetails;

public interface OpinionDetailsRepository extends JpaRepository<OpinionDetails, Long> {

	OpinionDetails findAllByOpinion(String opinion);

}
