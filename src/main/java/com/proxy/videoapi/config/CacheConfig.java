package com.proxy.videoapi.config;

import com.github.benmanes.caffeine.cache.Caffeine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

	@Value("${videoapi.cache.ttl}")
	private Long duration;

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager("recentVideos", "recentVideosByEtag");
		cacheManager.setCaffeine(caffeineCacheBuilder());
		cacheManager.setAsyncCacheMode(true);

		return cacheManager;
	}

	private Caffeine<Object, Object> caffeineCacheBuilder() {
		return Caffeine.newBuilder().initialCapacity(200) // Initial cache size
				.maximumSize(1000) // Maximum cache size
				.expireAfterWrite(duration, TimeUnit.MINUTES) // Cache entries expiration time
				.recordStats() // Enable cache statistics
				.executor(Runnable::run);
	}
}
