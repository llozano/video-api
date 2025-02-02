package com.proxy.videoapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proxy.videoapi.youtube.service.YouTubeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VideoService {

	@Autowired
	private YouTubeService youTubeService;
}
