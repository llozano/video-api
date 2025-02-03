package com.proxy.videoapi.youtube.model;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Snippet implements Serializable {
	private static final long serialVersionUID = -3631857008197214618L;

	private String publishedAt;

	private String channelId;

	private String title;

	private String description;

	private Map<String, Thumbnail> thumbnails;

	private String channeltitle;

	private String publishTime;
}
