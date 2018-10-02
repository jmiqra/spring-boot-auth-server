package com.asraf.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.IOP.CodecPackage.FormatMismatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.asraf.utils.HttpServletUtils;
import com.asraf.utils.JwtUtils;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ClaimFilter implements Filter {

	private JwtUtils jwtUtils;

	@Autowired
	public ClaimFilter(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletResponse response = (HttpServletResponse) res;
		final HttpServletRequest request = (HttpServletRequest) req;

		try {
			Object payloadAudience = jwtUtils.getPayloadValue("aud", request);
			String requestedAudience = HttpServletUtils.getRefererBaseUrl(request);
			if ((requestedAudience != null && payloadAudience != null
					&& !payloadAudience.toString().equals(requestedAudience))
					|| (requestedAudience != payloadAudience)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "Audience mismatch");
			}
		} catch (FormatMismatch e) {
			e.printStackTrace();
		}

		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}
}