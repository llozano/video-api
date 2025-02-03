package com.proxy.videoapi.model;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class ApiRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String channelId;
	private Optional<String> eTag;
	private UUID referenceId;

	public ApiRequest(String channelId, Optional<String> eTag) {
		this.channelId = channelId;
		this.eTag = eTag;
		this.referenceId = UUID.randomUUID();
	}

	public boolean isValid() {
		return channelId != null && !channelId.isEmpty() && !channelId.isBlank() && channelId.length() <= 100;
	}

}
