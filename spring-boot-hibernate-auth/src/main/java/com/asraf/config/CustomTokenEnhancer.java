package com.asraf.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.asraf.entities.User;
import com.asraf.entities.UserClaim;
import com.asraf.services.UserClaimService;
import com.asraf.services.UserService;
import com.asraf.utils.HttpServletUtils;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UserService userService;
	@Autowired
	private UserClaimService userClaimService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> additionalInfo = new HashMap<>();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		// String clientId =
		// decodeClientId(request.getHeader(HttpHeaders.AUTHORIZATION));
		additionalInfo.put("exp", accessToken.getExpiration());
		this.loadRequestRelatedClaims(request, additionalInfo);
		this.loadUserRelatedClaims(authentication.getName(), additionalInfo);

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}

	// private String decodeClientId(final String authorization) {
	// String base64Credentials = authorization.substring("Basic".length()).trim();
	// byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
	// String credentials = new String(credDecoded, StandardCharsets.UTF_8);
	// // credentials = username:password
	// final String[] clientCredentials = credentials.split(":", 2);
	// return clientCredentials[0];
	// }

	private void loadUserRelatedClaims(String username, Map<String, Object> additionalInfo) {
		User user = userService.getByUsername(username);
		if (user == null) {
			return;
		}
		additionalInfo.put("sub", user.getId());
		List<UserClaim> userClaims = userClaimService.getByUserId(user.getId());
		userClaims.forEach(uc -> {
			additionalInfo.put(uc.getClaimType(), uc.getClaimValue());
		});
	}

	private void loadRequestRelatedClaims(HttpServletRequest request, Map<String, Object> additionalInfo) {
		additionalInfo.put("iss", HttpServletUtils.getBaseUrl(request));
		additionalInfo.put("aud", HttpServletUtils.getRefererBaseUrl(request));
	}

	
}