package com.proxy.videoapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proxy.videoapi.model.ChannelResult;

public interface ChannelResultRepository extends JpaRepository<ChannelResult, Long> {

	Optional<ChannelResult> findByChannelId(String channelId);

	@Query(value = "SELECT cr FROM ChannelResult cr"
			+ " WHERE cr.updatedOn >= :last30min"
			+ " ORDER BY e.updatedOn DESC"
			+ " LIMIT :limit", nativeQuery = true)
	List<ChannelResult> latestChannelResultsSortedDesc(@Param("last30min") LocalDate last30min,
			@Param("limit") Integer limit);

}
