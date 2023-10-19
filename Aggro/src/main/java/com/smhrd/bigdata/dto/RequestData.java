package com.smhrd.bigdata.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public abstract class RequestData {
	
	public int statusCode = 200;
	
	Object data;
	
}
