package com.proxy.videoapi.model;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class ApiRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private String version;
	private String channelId;
	private Optional<String> eTag;
	private UUID referenceId;

	public ApiRequest(String version, String channelId, Optional<String> eTag) {

		/**
		 * Api version
		 */
		this.version = version;

		/**
		 * Video channel Id
		 */
		this.channelId = channelId;

		/**
		 * Resource eTag
		 */
		this.eTag = eTag;
		this.referenceId = UUID.randomUUID();
	}

	/**
	 * Request validation
	 * @return true when required parameters are valid
	 */
	public boolean isValid() {
		return !StringUtils.isAnyBlank(version, channelId) && StringUtils.length(channelId) <= 100;
	}

}
