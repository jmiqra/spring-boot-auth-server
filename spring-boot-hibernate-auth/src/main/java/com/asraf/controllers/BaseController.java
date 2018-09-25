package com.asraf.controllers;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.utility.NullArgumentException;

public abstract class BaseController {

	private final String TOKEN_TYPE = "Bearer ";
	private final TypeReference<HashMap<String, Object>> TYPE_REF = new TypeReference<HashMap<String, Object>>() {
	};
	private HashMap<String, Object> tokenPayload = null;

	protected long getCurrentUserId() throws JsonParseException, JsonMappingException, IOException {
		this.loadTokenPayloadIfNeeded();
		long userId = Long.parseLong(tokenPayload.get("sub").toString());
		return userId;
	}

	private void loadTokenPayloadIfNeeded() throws JsonParseException, JsonMappingException, IOException {
		if (tokenPayload == null) {
			loadTokenPayload();
		}
	}

	private void loadTokenPayload() throws JsonParseException, JsonMappingException, IOException {
		String jwt = getTokenFromHeader();
		String payload = jwt.substring(jwt.indexOf('.') + 1, jwt.lastIndexOf('.'));
		byte[] decodedBytes = Base64.getMimeDecoder().decode(payload);
		String decodedString = new String(decodedBytes);
		tokenPayload = new ObjectMapper().readValue(decodedString, TYPE_REF);
	}

	private String getTokenFromHeader() throws NullArgumentException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader != null
				&& (authHeader.startsWith(TOKEN_TYPE) || authHeader.startsWith(TOKEN_TYPE.toLowerCase()))) {
			return authHeader.substring(TOKEN_TYPE.length());
		}
		throw new NullArgumentException(HttpHeaders.AUTHORIZATION, "No bearer token found");
	}

}
