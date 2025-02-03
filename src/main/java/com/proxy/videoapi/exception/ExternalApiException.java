package com.proxy.videoapi.exception;

import com.proxy.videoapi.model.ApiRequest;

import lombok.Getter;
import lombok.ToString;

@ToString
public class ExternalApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	@Getter
	private ApiRequest apiRequest;
	
	@Getter
	private String message;

	public ExternalApiException(Throwable throwable, ApiRequest apiRequest) {
		super(throwable.getMessage(), throwable);
		this.apiRequest = apiRequest;
		this.message = "Error reaching out to downstream API";
	}

}
