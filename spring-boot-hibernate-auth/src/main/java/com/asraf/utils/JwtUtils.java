package com.asraf.utils;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.utility.NullArgumentException;

@Component
public class JwtUtils {

	private final String TOKEN_TYPE = "Bearer ";
	private final TypeReference<HashMap<String, Object>> TYPE_REF = new TypeReference<HashMap<String, Object>>() {
	};

	private HttpServletRequest request;

	@Autowired
	public JwtUtils(HttpServletRequest request) {
		this.request = request;
	}

	public long getCurrentUserId() throws JsonParseException, JsonMappingException, IOException {
		long userId = Long.parseLong(getPayload().get("sub").toString());
		return userId;
	}

	private HashMap<String, Object> getPayload() throws JsonParseException, JsonMappingException, IOException {
		String decodedString = JwtHelper.decode(getTokenFromHeader()).getClaims();
		return new ObjectMapper().readValue(decodedString, TYPE_REF);
	}

	private String getTokenFromHeader() throws NullArgumentException {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader != null
				&& (authHeader.startsWith(TOKEN_TYPE) || authHeader.startsWith(TOKEN_TYPE.toLowerCase()))) {
			return authHeader.substring(TOKEN_TYPE.length());
		}
		throw new NullArgumentException(HttpHeaders.AUTHORIZATION, "No bearer token found");
	}

}
