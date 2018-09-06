package com.asraf.config;

import org.springframework.security.oauth2.provider.token.AccessTokenConverter;

public interface CustomAccessTokenConverter extends AccessTokenConverter {

	final String TOKEN_TYPE = "token_type";
	final String RTI = "rti";

}
