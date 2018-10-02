package com.asraf.resources.main;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;

import com.asraf.controllers.AccountController;
import com.asraf.controllers.OauthClientDetailsController;
import com.asraf.controllers.RoleController;
import com.asraf.controllers.UserClaimController;
import com.asraf.controllers.UserController;
import com.asraf.controllers.UserVerificationController;
import com.asraf.resources.BaseResource;
import com.asraf.resources.ExtendedLink;
import com.asraf.utils.HttpServletUtils;

public class MainAuthenticatedResource extends BaseResource {

	public MainAuthenticatedResource() {

		add(new ExtendedLink(linkTo(AccountController.class).withRel("accounts")).withMethod(HttpMethod.GET)
				.withSearchableData());

		add(new ExtendedLink(linkTo(UserController.class).withRel("users")).withMethod(HttpMethod.GET)
				.withSearchableData());

		add(new ExtendedLink(linkTo(UserVerificationController.class).withRel("user-verifications"))
				.withMethod(HttpMethod.GET).withSearchableData());

		add(new ExtendedLink(linkTo(UserClaimController.class).withRel("user-claims")).withMethod(HttpMethod.GET)
				.withSearchableData());

		add(new ExtendedLink(linkTo(RoleController.class).withRel("roles")).withMethod(HttpMethod.GET)
				.withSearchableData());

		add(new ExtendedLink(linkTo(OauthClientDetailsController.class).withRel("oauth-clients"))
				.withMethod(HttpMethod.GET).withSearchableData());

		String baseUrl = HttpServletUtils.getBaseUrl();
		add(new ExtendedLink(new Link(baseUrl + "/oauth/token").withRel("token")).withMethod(HttpMethod.GET));

	}

}