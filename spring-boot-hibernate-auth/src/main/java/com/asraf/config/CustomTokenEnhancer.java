package com.asraf.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.asraf.entities.User;
import com.asraf.services.UserService;
import com.asraf.utils.StringUtils;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UserService userService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();

		additionalInfo.put("exp", accessToken.getExpiration());
		this.loadRequestRelatedClaims(additionalInfo);
		this.loadUserRelatedClaims(authentication.getName(), additionalInfo);

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}

	private void loadUserRelatedClaims(String username, Map<String, Object> additionalInfo) {
		User user = userService.getByUsername(username);
		additionalInfo.put("sub", user == null ? null : user.getId());
	}

	private void loadRequestRelatedClaims(Map<String, Object> additionalInfo) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String baseUrlOfIssuer = request.getRequestURL().toString().replace(request.getRequestURI(),
				request.getContextPath());
		additionalInfo.put("iss", baseUrlOfIssuer);
		additionalInfo.put("aud", getRefererBaseUrl(request));
	}

	private String getRefererBaseUrl(HttpServletRequest request) {
		String refererUrlStr = request.getHeader(HttpHeaders.REFERER);
		if (StringUtils.isNullOrEmpty(refererUrlStr)) {
			return null;
		}
		try {
			URL refererUrl = new URL(refererUrlStr);
			String baseUrlOfAudience = refererUrlStr.replaceAll(refererUrl.getPath(), "");
			return baseUrlOfAudience;
		} catch (MalformedURLException e) {
			return "NO BASE URL FOUND TO SET AUDIENCE";
		}
	}
}