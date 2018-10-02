package com.asraf.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.mail.MessagingException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asraf.constants.RolePreAuthorizeConditions;
import com.asraf.dtos.request.main.TokenRequestDto;
import com.asraf.dtos.response.requestdto.RequestBodyResponseDto;
import com.asraf.dtos.response.requestdto.RequestDataCollectionResponseDto;
import com.asraf.resources.main.MainAnonymousResource;
import com.asraf.resources.main.MainAuthenticatedResource;
import com.asraf.utils.HttpServletUtils;

@RestController
@RequestMapping("")
public class MainController {

	@GetMapping("")
	public MainAnonymousResource getAnonymousLinks() throws UnsupportedEncodingException, MessagingException {
		return new MainAnonymousResource();
	}

	@GetMapping("/authenticated-links")
	public MainAuthenticatedResource getAuthenticatedLinks() {
		return new MainAuthenticatedResource();
	}

	@GetMapping("/{id}")
	public String getById(@PathVariable("id") String id) {
		return "getById -> " + id;
	}

	@PostMapping("")
	public String create() {
		return "create";
	}

	@PutMapping("")
	public String update() {
		return "update";
	}

	@PreAuthorize(RolePreAuthorizeConditions.ADMIN)
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") String id) {
		return "delete -> " + id;
	}

	@GetMapping("/requests")
	public RequestDataCollectionResponseDto getRequests() throws URISyntaxException {
		RequestDataCollectionResponseDto requestDataCollection = new RequestDataCollectionResponseDto();
		this.addRequestDataOfPost(requestDataCollection);
		return requestDataCollection;
	}

	private MainController addRequestDataOfPost(RequestDataCollectionResponseDto requestDataCollection)
			throws URISyntaxException {
		RequestBodyResponseDto<TokenRequestDto> requestBody = new RequestBodyResponseDto<TokenRequestDto>(
				TokenRequestDto.class);
		String baseUrl = HttpServletUtils.getBaseUrl();
		URI uri = new URI(baseUrl + "/oauth/token");
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.POST, requestBody);
		return this;
	}

}
