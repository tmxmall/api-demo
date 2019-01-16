package com.tmxmall.fileapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class JacksonUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef) throws IOException {
		return objectMapper.readValue(jsonStr, valueTypeRef);
	}

	public static String toJSon(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
}
