package com.proxy.videoapi.controller;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.proxy.videoapi.exception.ExternalApiException;
import com.proxy.videoapi.exception.InternalApiException;
import com.proxy.videoapi.exception.ResponseException;
import com.proxy.videoapi.model.ApiRequest;
import com.proxy.videoapi.model.ChannelResultDTO;
import com.proxy.videoapi.service.VideoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/{version}/videos")
public class VideoApiController {

	@Value("${videoapi.cache.ttl}")
	private Long duration;

	@Autowired
	VideoService videoService;

	@GetMapping(path = "/{channelId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<?>> search(@PathVariable final String version,
			@PathVariable final String channelId,
			@RequestHeader(name = "If-None-Match", required = false) final Optional<String> eTag) {

		final ApiRequest apiRequest = new ApiRequest(version, channelId, eTag);

		log.info("Attending API request: " + apiRequest);

		if (!apiRequest.isValid()) {
			throw new ResponseException(apiRequest, HttpStatus.BAD_REQUEST);
		}

		return videoService.search(apiRequest).thenApply((result) -> {
			if (result.isEmpty()) {
				log.warn("Request resolved as NOT FOUND");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}

			final ChannelResultDTO dto = result.get();
			// Prepare response headers
			final HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("X-Request-ID", apiRequest.getReferenceId().toString());

			if (eTag.isPresent() && dto.getETag().equals(eTag.get())) {
				return ResponseEntity.status(HttpStatus.NOT_MODIFIED).headers(httpHeaders).build();
			}

			return ResponseEntity.ok().eTag(dto.getETag()).headers(httpHeaders)
					.cacheControl(CacheControl.maxAge(duration, TimeUnit.MINUTES)).body(dto);
		}).exceptionally(ex -> {
			Throwable cause = ex.getCause();
			if (cause instanceof RestClientException) {
				throw new ExternalApiException(ex.getCause(), apiRequest);
			}

			if (cause instanceof ResponseException) {
				throw new ResponseException(apiRequest, ((ResponseException) cause).getHttpStatus());
			}

			throw new InternalApiException(cause, apiRequest);
		});
	}
}
