package com.proxy.videoapi.converter;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proxy.videoapi.youtube.model.SearchResult;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class SearchResultConverter implements AttributeConverter<List<SearchResult>, String> {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(List<SearchResult> searchResults) {
		try {
			return objectMapper.writeValueAsString(searchResults);
		} catch (JsonProcessingException ex) {
			log.error("Error converting searchResults to json", ex);
		}
		return "[]";
	}

	@Override
	public List<SearchResult> convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readerForListOf(SearchResult.class).readValue(dbData);
		} catch (JsonProcessingException ex) {
			log.error("Error converting dbData to SearchResult", ex);
		}
		return null;
	}

}
