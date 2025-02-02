package com.divya29.productservice.dtos.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

public class ESUtils {

	public static <T> Optional<T> mapForES(Class<T> type, T input) {
		ObjectMapper mapper = getObjectMapper();
		try {
			return Optional.ofNullable(mapper.readValue(mapper.writeValueAsString(input), type));
		} catch (JsonProcessingException e) {
			return Optional.empty();
		}
	}

	// USAGE List<T> categoryList = ESUtils.mapListForES(Category.class,
	// categoryPage.getContent());
	// elasticsearchRepository.saveAll(categoryList);
	public static <T> List<T> mapListForES(Class<T> type, List<T> input) {
		ObjectMapper mapper = getObjectMapper();
		try {
			JavaType javaType = mapper.getTypeFactory().constructCollectionType(List.class, type);
			String serialText = mapper.writeValueAsString(input);
			return mapper.readValue(serialText, javaType);
		} catch (JsonProcessingException e) {
			return new ArrayList<>();
		}
	}

	@NotNull
	private static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Hibernate5Module module = new Hibernate5Module();
		module.disable(Hibernate5Module.Feature.FORCE_LAZY_LOADING);
		module.enable(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS);
		module.enable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
		module.enable(Hibernate5Module.Feature.REPLACE_PERSISTENT_COLLECTIONS);
		return mapper;
	}
}