package com.proxy.videoapi.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.proxy.videoapi.exception.ExternalApiException;
import com.proxy.videoapi.exception.InternalApiException;
import com.proxy.videoapi.exception.ResponseException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value = ResponseException.class)
	public ResponseEntity<?> handleResponseException(ResponseException responseException) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("referenceId", responseException.getApiRequest().getReferenceId().toString());
		return ResponseEntity.status(responseException.getHttpStatus()).headers(httpHeaders).build();
	}

	@ExceptionHandler(value = ExternalApiException.class)
	public ResponseEntity<?> handleExternalApiException(ExternalApiException externalApiException) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("referenceId", externalApiException.getApiRequest().getReferenceId().toString());

		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		if (externalApiException.getCause() instanceof HttpServerErrorException) {
			final HttpServerErrorException ex = (HttpServerErrorException) externalApiException.getCause();
			httpStatus = HttpStatus.valueOf(ex.getStatusCode().value());
		} else if (externalApiException.getCause() instanceof HttpClientErrorException) {
			final HttpClientErrorException ex = (HttpClientErrorException) externalApiException.getCause();
			httpStatus = HttpStatus.valueOf(ex.getStatusCode().value());
		}

		return ResponseEntity.status(httpStatus).headers(httpHeaders).body(externalApiException);
	}

	@ExceptionHandler(value = InternalApiException.class)
	public ResponseEntity<?> handleResponseException(InternalApiException internalApiException) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("referenceId", internalApiException.getApiRequest().getReferenceId().toString());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(httpHeaders).build();
	}
}
