package com.asraf.utils;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.omg.IOP.CodecPackage.FormatMismatch;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope(value = "prototype")
public class JwtUtils {

	private final String TOKEN_TYPE = "Bearer ";
	private final TypeReference<HashMap<String, Object>> TYPE_REF = new TypeReference<HashMap<String, Object>>() {
	};

	private String previousJwt = null;
	private HashMap<String, Object> tokenPayload = null;

	public JwtUtils() {
		this.tokenPayload = null;
		this.previousJwt = null;
	}

	public Object getPayloadValue(String payloadKey)
			throws JsonParseException, JsonMappingException, IOException, FormatMismatch {
		this.loadTokenPayloadIfNeeded(this.getRequest());
		return tokenPayload.get(payloadKey);
	}

	public Object getPayloadValue(String payloadKey, HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException, FormatMismatch {
		this.loadTokenPayloadIfNeeded(request);
		return tokenPayload.get(payloadKey);
	}

	public long getCurrentUserId() throws JsonParseException, JsonMappingException, IOException, FormatMismatch {
		this.loadTokenPayloadIfNeeded(this.getRequest());
		long userId = Long.parseLong(tokenPayload.get("sub").toString());
		return userId;
	}

	private void loadTokenPayloadIfNeeded(HttpServletRequest request)
			throws JsonParseException, JsonMappingException, IOException, FormatMismatch {
		String currentJwt = getTokenFromHeader(request);
		if (!currentJwt.equals(this.previousJwt)) {
			String decodedString = JwtHelper.decode(currentJwt).getClaims();
			tokenPayload = new ObjectMapper().readValue(decodedString, TYPE_REF);
			this.previousJwt = currentJwt;
		}
	}

	private String getTokenFromHeader(HttpServletRequest request) throws FormatMismatch {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader != null
				&& (authHeader.startsWith(TOKEN_TYPE) || authHeader.startsWith(TOKEN_TYPE.toLowerCase()))) {
			return authHeader.substring(TOKEN_TYPE.length());
		}
		throw new FormatMismatch("No 'bearer' token found in " + HttpHeaders.AUTHORIZATION + " header");
	}

	private HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

}
