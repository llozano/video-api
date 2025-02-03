package com.proxy.videoapi.youtube.service;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.proxy.videoapi.youtube.model.SearchResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class YouTubeService {

	@Autowired
	@Qualifier("youTubeApiRestTemplate")
	private RestTemplate restTemplate;

	@Autowired
	@Qualifier("threadPollTaskExecutor")
	private Executor restExecutor;

	@Value("${youtube.api.url}")
	private String youTubeUrl;

	@Value("${youtube.api.search.maxResults}")
	private Integer maxResults;

	@Value("${youtube.api.search.part}")
	private String part;

	public CompletableFuture<ResponseEntity<SearchResponse>> featchVideosForChannel(String channelId,
			Optional<String> eTag) throws CompletionException, CancellationException {
		final URI uri = buildSearchUri(channelId);

		return executeSearch(uri, eTag);
	}

	private URI buildSearchUri(String channelId) throws RuntimeException {
		return UriComponentsBuilder.fromUriString(youTubeUrl).queryParam("maxResults", maxResults)
				.queryParam("part", part).queryParam("channelId", channelId).build().encode().toUri();
	}

	private CompletableFuture<ResponseEntity<SearchResponse>> executeSearch(URI uri, Optional<String> eTag) {
		return CompletableFuture.supplyAsync(() -> {

			final HttpHeaders httpHeaders = new HttpHeaders();
			if (eTag.isPresent()) {
				httpHeaders.set("If-None-Match", eTag.get());
			}

			final HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

			return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, SearchResponse.class);

		}, restExecutor);
	}

}
