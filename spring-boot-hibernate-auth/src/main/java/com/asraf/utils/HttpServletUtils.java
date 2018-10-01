package com.asraf.utils;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
			return "NO BASE URL FOUND FOR REFERER";
		}
	}

	public static String getBaseUrl() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
		return baseUrl;
	}

	public static String getBaseUrl(HttpServletRequest request) {
		String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath());
		return baseUrl;
	}

}
