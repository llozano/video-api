package com.proxy.videoapi.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proxy.videoapi.youtube.model.SearchResult;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
public class SearchResultConverter implements AttributeConverter<SearchResult, String> {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(SearchResult attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException ex) {
			log.error(String.format("Error converting searchResult to ", attribute.getId()), ex);
		}
		return "{}";
	}

	@Override
	public SearchResult convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, SearchResult.class);
		} catch (JsonProcessingException ex) {
			log.error("Error converting dbData to SearchResul ", ex);
		}
		return null;
	}

}
