package com.proxy.videoapi.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.proxy.videoapi.converter.SearchResultConverter;
import com.proxy.videoapi.youtube.model.SearchResult;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(schema = "video", name = "channel_result")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ChannelResult implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue
    private Long id;
	
	@Column(nullable = false)
	private String channelId;
	
	@Column(name = "search_etag", nullable = false)
	private String searchETag;
	
	@Convert(converter = SearchResultConverter.class)
	@Column(columnDefinition = "json", nullable = false)
	private List<SearchResult> items;
	
	@Column(columnDefinition = "TIMESTAMP")
	private Instant createdOn;
	
	@Column(columnDefinition = "TIMESTAMP", nullable = true)
	private Instant updatedOn;

}
