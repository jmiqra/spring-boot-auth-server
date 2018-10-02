package com.asraf.resources.main;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import javax.mail.MessagingException;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;

import com.asraf.controllers.AccountController;
import com.asraf.controllers.MainController;
import com.asraf.resources.BaseResource;
import com.asraf.resources.ExtendedLink;
import com.asraf.utils.HttpServletUtils;

public class MainAnonymousResource extends BaseResource {

	public MainAnonymousResource() throws UnsupportedEncodingException, MessagingException, URISyntaxException {

		String baseUrl = HttpServletUtils.getBaseUrl();

		add(new ExtendedLink(new Link(baseUrl).withSelfRel()).withMethod(HttpMethod.GET).withNullFormatAndFields());

		add(new ExtendedLink(new Link(baseUrl + "/oauth/token").withRel("access-token-url")).withMethod(HttpMethod.POST)
				.withNullFormatAndFields());

		add(new ExtendedLink(new Link(baseUrl + "/oauth/authorize").withRel("auth-url")).withNullFormatAndFields());

		add(new ExtendedLink(
				linkTo(methodOn(MainController.class).getAuthenticatedLinks()).withRel("authenticated-links"))
						.withMethod(HttpMethod.GET).withNullFormatAndFields());

		add(new ExtendedLink(linkTo(methodOn(MainController.class).getRequests()).withRel("requests"))
				.withMethod(HttpMethod.GET));

		add(new ExtendedLink(linkTo(methodOn(AccountController.class).createUser(null)).withRel("sign-up"))
				.withMethod(HttpMethod.POST));

		add(new ExtendedLink(linkTo(methodOn(AccountController.class).forgotPassword(null)).withRel("forgot-password"))
				.withMethod(HttpMethod.POST));

	}

}