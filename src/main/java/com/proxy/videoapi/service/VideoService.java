package com.proxy.videoapi.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import com.proxy.videoapi.exception.ExternalApiException;
import com.proxy.videoapi.exception.InternalApiException;
import com.proxy.videoapi.model.ApiRequest;
import com.proxy.videoapi.model.ChannelResult;
import com.proxy.videoapi.model.ChannelResultDTO;
import com.proxy.videoapi.repository.ChannelResultRepository;
import com.proxy.videoapi.youtube.model.SearchResponse;
import com.proxy.videoapi.youtube.service.YouTubeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VideoService {

	@Value("${videoapi.cache.ttl}")
	private Long duration;

	@Autowired
	private ChannelResultRepository channelResultRepository;

	@Autowired
	private YouTubeService youTubeService;
	
	
	@Async
	@Cacheable(value = "recentVideos", key = "#apiRequest.channelId")
	public CompletableFuture<Optional<ChannelResultDTO>> search(ApiRequest apiRequest) {
		Optional<ChannelResult> channelResult = Optional.empty();
		Optional<ChannelResultDTO> dto = Optional.empty();

		try {
			log.info("Accessing the db. " + apiRequest);
			channelResult = channelResultRepository.findByChannelId(apiRequest.getChannelId());
			final Instant acceptableTtl = Instant.now().minus(duration, ChronoUnit.MINUTES);

			if (channelResult.isPresent()) {
				log.info("channelResult found in the db. " + apiRequest);
				final ChannelResult foundChannelResult = channelResult.get();
				if (foundChannelResult.getUpdatedOn().isAfter(acceptableTtl)) {
					// Data is still under the estimated time that allows to call it as "fresh data"
					// :)
					dto = Optional.of(ChannelResultDTO.fromEntity(foundChannelResult));
				}
			}

			if (dto.isEmpty()) {
				log.info("[1] Retrieving data from YouTube. " + apiRequest);
				ResponseEntity<SearchResponse> responseEntity = youTubeService
						.featchVideosForChannel(apiRequest.getChannelId(), apiRequest.getETag()).join();

				if (responseEntity.getStatusCode().isSameCodeAs(HttpStatus.NOT_MODIFIED)) {
					// Worst case: record isn't cached not found in the db (for some reason) and
					// YouTube data search result has not changed
					log.info("<[2]> Retrieving data from YouTube. " + apiRequest);
					responseEntity = youTubeService.featchVideosForChannel(apiRequest.getChannelId(), Optional.empty())
							.join();
				}

				final SearchResponse searchResponse = responseEntity.getBody();
				dto = Optional.of(ChannelResultDTO.fromSeachResponse(apiRequest.getChannelId(), searchResponse));

				log.info("[BEGIN] - Storing to db");
				if (channelResult.isPresent()) {

					final ChannelResult toUpdate = channelResult.get();
					toUpdate.setItems(searchResponse.getItems());
					toUpdate.setSearchETag(searchResponse.getEtag());
					toUpdate.setUpdatedOn(Instant.now());

					log.info("Updating result: " + toUpdate.getId());
					channelResultRepository.save(toUpdate);
				} else {
					Instant now = Instant.now();
					final ChannelResult toSave = ChannelResult.builder().channelId(apiRequest.getChannelId())
							.searchETag(searchResponse.getEtag()).items(searchResponse.getItems()).createdOn(now)
							.updatedOn(now).build();
					
					log.info("Inserting new result: " + toSave.getChannelId());
					channelResultRepository.save(toSave);
				}
				log.info("[END] - Storing to db");
			}

		} catch (RestClientException restEx) {
			log.error(String.format("Error fetching for request. Reference: %s", apiRequest.getReferenceId()), restEx);
			// [**] if response is ready, serve it instead of responding with exception
			if (dto.isEmpty()) {
				throw new ExternalApiException(restEx.getCause(), apiRequest);
			}
		} catch (CompletionException compEx) {
			log.error(String.format("Error executing request. Reference: %s", apiRequest.getReferenceId()), compEx);
			// [**]
			if (dto.isEmpty()) {
				throw new InternalApiException(compEx.getCause(), apiRequest);
			}

		} catch (Exception ex) {
			log.error(String.format("Error ocurred. Reference: %s", apiRequest.getReferenceId()), ex);
			// [**]
			if (dto.isEmpty()) {
				throw new InternalApiException(ex.getCause(), apiRequest);
			}
		}

		return CompletableFuture.completedFuture(dto);
	}
}
