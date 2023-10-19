package com.smhrd.bigdata.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class RequestBoardList {

	private int statusCode = 200;
	
	List<Object> data;
	
}
