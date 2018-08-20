package com.asraf.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.asraf.dtos.mapper.UserVerificationMapper;
import com.asraf.dtos.request.entities.UserVerificationRequestDto;
import com.asraf.dtos.response.requestdto.RequestBodyResponseDto;
import com.asraf.dtos.response.requestdto.RequestDataCollectionResponseDto;
import com.asraf.entities.UserVerification;
import com.asraf.resources.assemblers.entities.UserVerificationResourceAssembler;
import com.asraf.resources.entities.UserVerificationResource;
import com.asraf.services.UserVerificationService;

@RestController
@RequestMapping("/user-verifications")
public class UserVerificationController extends BaseController {

	private final UserVerificationService userVerificationService;
	private final UserVerificationMapper userVerificationMappper;
	private final UserVerificationResourceAssembler userVerificationResourceAssembler;

	@Autowired
	public UserVerificationController(UserVerificationService userVerificationService,
			UserVerificationMapper userVerificationMappper,
			UserVerificationResourceAssembler userVerificationResourceAssembler) {
		this.userVerificationMappper = userVerificationMappper;
		this.userVerificationService = userVerificationService;
		this.userVerificationResourceAssembler = userVerificationResourceAssembler;
	}

	@GetMapping("")
	public PagedResources<UserVerificationResource> getByQuery(String search, Pageable pageable,
			PagedResourcesAssembler<UserVerification> pagedAssembler) {
		Page<UserVerification> userVerifications = userVerificationService.getByQuery(search, pageable);
		return pagedAssembler.toResource(userVerifications, this.userVerificationResourceAssembler);
	}

	@GetMapping("/all")
	public List<UserVerificationResource> getAll() {
		List<UserVerification> userVerifications = (List<UserVerification>) this.userVerificationService.getAll();
		return this.userVerificationResourceAssembler.toResources(userVerifications);
	}

	@GetMapping("/{id}")
	public UserVerificationResource getById(@PathVariable long id) {
		UserVerification userVerification = userVerificationService.getById(id);
		return this.userVerificationResourceAssembler.toResource(userVerification);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public UserVerificationResource create(@Valid @RequestBody UserVerificationRequestDto requestDto) {
		UserVerification userVerification = userVerificationMappper.getEntity(requestDto);
		userVerificationService.save(userVerification);
		return this.userVerificationResourceAssembler.toResource(userVerification);
	}

	@DeleteMapping("/{id}")
	public UserVerificationResource delete(@PathVariable long id) {
		UserVerification userVerification = userVerificationService.getById(id);
		UserVerificationResource response = this.userVerificationResourceAssembler.toResource(userVerification)
				.forDeletion();
		userVerificationService.delete(userVerification);
		return response;
	}

	@PutMapping("/{id}")
	public UserVerificationResource update(@PathVariable long id,
			@Valid @RequestBody UserVerificationRequestDto requestDto) {
		UserVerification userVerification = userVerificationService.getById(id);
		userVerificationMappper.loadEntity(requestDto, userVerification);
		userVerificationService.save(userVerification);
		return this.userVerificationResourceAssembler.toResource(userVerification);
	}

	@GetMapping("/requests")
	public RequestDataCollectionResponseDto getRequests() {
		RequestDataCollectionResponseDto requestDataCollection = new RequestDataCollectionResponseDto();
		this.addRequestDataOfPost(requestDataCollection);
		return requestDataCollection;
	}

	private UserVerificationController addRequestDataOfPost(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<UserVerificationRequestDto> requestBody = new RequestBodyResponseDto<UserVerificationRequestDto>(
				UserVerificationRequestDto.class);
		URI uri = linkTo(methodOn(UserVerificationController.class).create(null)).toUri();
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.POST, requestBody);
		return this;
	}

}
