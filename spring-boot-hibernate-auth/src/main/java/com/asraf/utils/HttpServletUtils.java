package com.asraf.utils;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;

public class HttpServletUtils {

	public static String getRefererBaseUrl(HttpServletRequest request) {
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
