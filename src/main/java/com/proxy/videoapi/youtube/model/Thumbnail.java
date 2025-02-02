package com.proxy.videoapi.youtube.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
@JsonInclude(Include.NON_NULL)
public class Thumbnail implements Serializable {
	private static final long serialVersionUID = 1L;

	private String url;

	private Integer width;

	private Integer height;
}
