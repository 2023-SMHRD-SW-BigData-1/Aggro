package com.smhrd.bigdata.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestBoardDetail {
	
	private int statusCode = 201;
	
	private Object data;
	
}
