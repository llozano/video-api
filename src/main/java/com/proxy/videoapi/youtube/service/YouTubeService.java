package com.proxy.videoapi.youtube.service;

import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
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
	private Executor executor;

	@Value("${youtube.api.url}")
	private String youTubeUrl;

	@Value("${youtube.api.search.maxResults}")
	private Integer maxResults;

	@Value("${youtube.api.search.part}")
	private String part;

	public SearchResponse featchVideosForChannel(String channelId) throws CompletionException, CancellationException {
		final URI uri = buildSearchUri(channelId);

		return executeSearch(uri).join();
	}

	private URI buildSearchUri(String channelId) throws RuntimeException {
		return UriComponentsBuilder.fromUriString(youTubeUrl).queryParam("maxResults", maxResults)
				.queryParam("part", part).queryParam("channelId", channelId).build().encode().toUri();
	}

	private CompletableFuture<SearchResponse> executeSearch(URI uri) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return restTemplate.getForObject(uri, SearchResponse.class);
			} catch (RestClientException rex) {
				log.error("Error HTTP client", rex);
				throw new CompletionException(rex.getCause());
			} catch (Exception ex) {
				log.error("Error", ex);
				throw new RuntimeException(ex.getCause());
			}
		}, executor);
	}

}
