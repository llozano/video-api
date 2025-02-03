package com.proxy.videoapi.config;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.proxy.videoapi.model.ChannelResult;
import com.proxy.videoapi.model.ChannelResultDTO;
import com.proxy.videoapi.repository.ChannelResultRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApiEventListener {

	@Value("${videoapi.cache.ttl}")
	private Long duration;

	@Autowired
	private ChannelResultRepository channelResultRepository;

	@Autowired
	private CacheManager cacheManager;

	@EventListener
	public void handleApplicationReady(ApplicationReadyEvent event) {
		log.info("Initializing cache");

		try {
			final Instant acceptableTtl = Instant.now().minus(duration, ChronoUnit.MINUTES);
			final List<ChannelResult> channelResults = channelResultRepository
					.latestChannelResultsSortedDesc(acceptableTtl, 50); // last 50 updated items 

			final Cache videoCache = cacheManager.getCache("recentVideos");

			for (ChannelResult channelResult : channelResults) {
				final ChannelResultDTO dto = ChannelResultDTO.fromEntity(channelResult);
				videoCache.put(dto.getChannelId(), Optional.of(dto));
			}
		} catch (Exception ex) {
			log.error("Error while loading cache. Life continues.", ex);
		}

	}
}
