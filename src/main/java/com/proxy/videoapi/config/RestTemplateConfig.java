package com.proxy.videoapi.config;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class RestTemplateConfig {

	@Value("${youtube.api.key}")
	private String youTubeKey;

	@Bean(name = "youTubeApiRestTemplate")
	public RestTemplate YouTubeRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getInterceptors().add(new YouTubeHttpRequestInterceptor());

		return restTemplate;
	}

	class YouTubeHttpRequestInterceptor implements ClientHttpRequestInterceptor {

		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
				throws IOException {

			final URI uri = UriComponentsBuilder.fromUri(request.getURI()).queryParam("key", youTubeKey).build()
					.toUri();

			final HttpRequest modifiedRequest = new HttpRequestWrapper(request) {
				@Override
				public URI getURI() {
					return uri;
				}
			};
			return execution.execute(modifiedRequest, body);
		}

	}
}
