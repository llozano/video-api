package com.proxy.videoapi.exception;

import com.proxy.videoapi.model.ApiRequest;

import lombok.Getter;

public class InternalApiException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	@Getter
	private ApiRequest apiRequest;

	public InternalApiException(Throwable throwable, ApiRequest apiRequest) {
		super(throwable.getMessage(), throwable);
		
		this.apiRequest = apiRequest;
	}
}
