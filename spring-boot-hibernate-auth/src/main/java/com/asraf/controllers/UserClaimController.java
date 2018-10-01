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

import com.asraf.dtos.mapper.UserClaimMapper;
import com.asraf.dtos.request.entities.UserClaimRequestDto;
import com.asraf.dtos.response.requestdto.RequestBodyResponseDto;
import com.asraf.dtos.response.requestdto.RequestDataCollectionResponseDto;
import com.asraf.entities.UserClaim;
import com.asraf.resources.assemblers.entities.UserClaimResourceAssembler;
import com.asraf.resources.entities.UserClaimResource;
import com.asraf.services.UserClaimService;

@RestController
@RequestMapping("/user-claims")
public class UserClaimController extends BaseController {

	private final UserClaimService userClaimService;
	private final UserClaimMapper userClaimMappper;
	private final UserClaimResourceAssembler userClaimResourceAssembler;

	@Autowired
	public UserClaimController(UserClaimService userClaimService, UserClaimMapper userClaimMappper,
			UserClaimResourceAssembler userClaimResourceAssembler) {
		this.userClaimMappper = userClaimMappper;
		this.userClaimService = userClaimService;
		this.userClaimResourceAssembler = userClaimResourceAssembler;
	}

	@GetMapping("")
	public PagedResources<UserClaimResource> getByQuery(String search, Pageable pageable,
			PagedResourcesAssembler<UserClaim> pagedAssembler) {
		Page<UserClaim> userClaims = userClaimService.getByQuery(search, pageable);
		return pagedAssembler.toResource(userClaims, this.userClaimResourceAssembler);
	}

	@GetMapping("/all")
	public List<UserClaimResource> getAll() {
		List<UserClaim> userClaims = (List<UserClaim>) this.userClaimService.getAll();
		return this.userClaimResourceAssembler.toResources(userClaims);
	}

	@GetMapping("/{id}")
	public UserClaimResource getById(@PathVariable long id) {
		UserClaim userClaim = userClaimService.getById(id);
		return this.userClaimResourceAssembler.toResource(userClaim);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public UserClaimResource create(@Valid @RequestBody UserClaimRequestDto requestDto) {
		UserClaim userClaim = userClaimMappper.getEntity(requestDto);
		userClaimService.save(userClaim);
		return this.userClaimResourceAssembler.toResource(userClaim);
	}

	@DeleteMapping("/{id}")
	public UserClaimResource delete(@PathVariable long id) {
		UserClaim userClaim = userClaimService.getById(id);
		UserClaimResource response = this.userClaimResourceAssembler.toResource(userClaim).forDeletion();
		userClaimService.delete(userClaim);
		return response;
	}

	@PutMapping("/{id}")
	public UserClaimResource update(@PathVariable long id, @Valid @RequestBody UserClaimRequestDto requestDto) {
		UserClaim userClaim = userClaimService.getById(id);
		userClaimMappper.loadEntity(requestDto, userClaim);
		userClaimService.save(userClaim);
		return this.userClaimResourceAssembler.toResource(userClaim);
	}

	@GetMapping("/requests")
	public RequestDataCollectionResponseDto getRequests() {
		RequestDataCollectionResponseDto requestDataCollection = new RequestDataCollectionResponseDto();
		this.addRequestDataOfPost(requestDataCollection);
		return requestDataCollection;
	}

	private UserClaimController addRequestDataOfPost(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<UserClaimRequestDto> requestBody = new RequestBodyResponseDto<UserClaimRequestDto>(
				UserClaimRequestDto.class);
		URI uri = linkTo(methodOn(UserClaimController.class).create(null)).toUri();
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.POST, requestBody);
		return this;
	}

}
