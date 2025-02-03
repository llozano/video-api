package com.proxy.videoapi.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proxy.videoapi.model.ChannelResult;

public interface ChannelResultRepository extends JpaRepository<ChannelResult, Long> {

	Optional<ChannelResult> findByChannelId(String channelId);

	@Query(value = "SELECT cr.* FROM video.channel_result cr"
			+ " WHERE cr.updated_on >= :lastXmins"
			+ " ORDER BY cr.updated_on DESC"
			+ " LIMIT :limit", nativeQuery = true)
	List<ChannelResult> latestChannelResultsSortedDesc(@Param("lastXmins") Instant lastXmins,
			@Param("limit") Integer limit);

}
