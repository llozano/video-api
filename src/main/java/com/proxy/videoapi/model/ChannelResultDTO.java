package com.proxy.videoapi.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.proxy.videoapi.youtube.model.SearchResponse;
import com.proxy.videoapi.youtube.model.SearchResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
public class ChannelResultDTO implements Serializable {
	private static final long serialVersionUID = 8844906513613330165L;

	private String channelId;
	
	private String eTag;
	
	private List<SearchResult> items;
	
	public static ChannelResultDTO fromEntity(ChannelResult entity) {
		return ChannelResultDTO.builder()
			.channelId(entity.getChannelId())
			.eTag(entity.getSearchETag())
			.items(entity.getItems())
			.build();
	}
	
	public static ChannelResultDTO fromSeachResponse(String channelId, SearchResponse searchResponse) {
		return ChannelResultDTO.builder()
				.channelId(channelId)
				.eTag(searchResponse.getEtag())
				.items(searchResponse.getItems())
				.build();
	}
}
