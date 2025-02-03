package com.proxy.videoapi.exception;

import org.springframework.http.HttpStatus;

import com.proxy.videoapi.model.ApiRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class ResponseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private ApiRequest apiRequest;
	private HttpStatus httpStatus;
}
