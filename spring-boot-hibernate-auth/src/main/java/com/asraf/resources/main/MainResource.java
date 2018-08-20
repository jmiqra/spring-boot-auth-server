package com.asraf.resources.main;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.http.HttpMethod;

import com.asraf.controllers.UserController;
import com.asraf.controllers.UserVerificationController;
import com.asraf.entities.UserProfile;
import com.asraf.resources.BaseResource;
import com.asraf.resources.ExtendedLink;

public class MainResource extends BaseResource {

	public MainResource() {

		add(new ExtendedLink(linkTo(UserController.class).withRel("users")).withMethod(HttpMethod.GET)
				.withSearchableData());

		add(new ExtendedLink(linkTo(UserVerificationController.class).withRel("user-verifications"))
				.withMethod(HttpMethod.GET).withSearchableData());

		add(new ExtendedLink(linkTo(UserProfile.class).withRel("user-profiles")).withMethod(HttpMethod.GET)
				.withSearchableData());

	}

}