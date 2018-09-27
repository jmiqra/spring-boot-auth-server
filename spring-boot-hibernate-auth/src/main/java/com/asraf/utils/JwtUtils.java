package com.asraf.utils;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.utility.NullArgumentException;

@Component
@Scope(value = "prototype")
public class JwtUtils {

	private final String TOKEN_TYPE = "Bearer ";
	private final TypeReference<HashMap<String, Object>> TYPE_REF = new TypeReference<HashMap<String, Object>>() {
	};

	private String previousJwt = null;
	private HashMap<String, Object> tokenPayload = null;

	private HttpServletRequest request;

	@Autowired
	public JwtUtils(HttpServletRequest request) {
		this.request = request;
		this.tokenPayload = null;
		this.previousJwt = null;
	}

	public long getCurrentUserId() throws JsonParseException, JsonMappingException, IOException {
		this.loadTokenPayloadIfNeeded();
		long userId = Long.parseLong(tokenPayload.get("sub").toString());
		return userId;
	}

	private void loadTokenPayloadIfNeeded() throws JsonParseException, JsonMappingException, IOException {
		String currentJwt = getTokenFromHeader();
		if (!currentJwt.equals(this.previousJwt)) {
			String decodedString = JwtHelper.decode(currentJwt).getClaims();
			tokenPayload = new ObjectMapper().readValue(decodedString, TYPE_REF);
			this.previousJwt = currentJwt;
		}
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
