package com.asraf.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;

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

import com.asraf.dtos.mapper.OauthClientDetailsMapper;
import com.asraf.dtos.request.entities.OauthClientDetailsRequestDto;
import com.asraf.dtos.response.requestdto.RequestBodyResponseDto;
import com.asraf.dtos.response.requestdto.RequestDataCollectionResponseDto;
import com.asraf.entities.OauthClientDetails;
import com.asraf.resources.assemblers.entities.OauthClientDetailsResourceAssembler;
import com.asraf.resources.entities.OauthClientDetailsResource;
import com.asraf.services.OauthClientDetailsService;

@RestController
@RequestMapping("/oauth-clients")
public class OauthClientDetailsController extends BaseController {

	private OauthClientDetailsService oauthClientDetailsService;
	private OauthClientDetailsMapper oauthClientDetailsMappper;

	private final OauthClientDetailsResourceAssembler oauthClientDetailsResourceAssembler;

	@Autowired
	public OauthClientDetailsController(OauthClientDetailsService oauthClientDetailsService,
			OauthClientDetailsMapper oauthClientDetailsMappper,
			OauthClientDetailsResourceAssembler oauthClientDetailsResourceAssembler) {
		this.oauthClientDetailsMappper = oauthClientDetailsMappper;
		this.oauthClientDetailsService = oauthClientDetailsService;
		this.oauthClientDetailsResourceAssembler = oauthClientDetailsResourceAssembler;
	}

	@GetMapping("")
	public PagedResources<OauthClientDetailsResource> getByQuery(String search, Pageable pageable,
			PagedResourcesAssembler<OauthClientDetails> pagedAssembler) {
		Page<OauthClientDetails> oauthClientDetailss = oauthClientDetailsService.getByQuery(search, pageable);
		return pagedAssembler.toResource(oauthClientDetailss, this.oauthClientDetailsResourceAssembler);
	}

	@GetMapping("/{id}")
	public OauthClientDetailsResource getById(@PathVariable String id) {
		OauthClientDetails oauthClientDetails = oauthClientDetailsService.getById(id);
		return this.oauthClientDetailsResourceAssembler.toResource(oauthClientDetails);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public OauthClientDetailsResource create(@Valid @RequestBody OauthClientDetailsRequestDto requestDto) {
		OauthClientDetails oauthClientDetails = oauthClientDetailsMappper.getEntity(requestDto);
		oauthClientDetailsService.save(oauthClientDetails);
		return this.oauthClientDetailsResourceAssembler.toResource(oauthClientDetails);
	}

	@DeleteMapping("/{id}")
	public OauthClientDetailsResource delete(@PathVariable String id) {
		OauthClientDetails oauthClientDetails = oauthClientDetailsService.getById(id);
		OauthClientDetailsResource response = this.oauthClientDetailsResourceAssembler.toResource(oauthClientDetails)
				.forDeletion();
		oauthClientDetailsService.delete(oauthClientDetails);
		return response;
	}

	@PutMapping("/{id}")
	public OauthClientDetailsResource update(@PathVariable String id,
			@Valid @RequestBody OauthClientDetailsRequestDto requestDto) {
		OauthClientDetails oauthClientDetails = oauthClientDetailsService.getById(id);
		oauthClientDetailsMappper.loadEntity(requestDto, oauthClientDetails);
		oauthClientDetailsService.save(oauthClientDetails);
		return this.oauthClientDetailsResourceAssembler.toResource(oauthClientDetails);
	}

	@GetMapping("/requests")
	public RequestDataCollectionResponseDto getRequests() {
		RequestDataCollectionResponseDto requestDataCollection = new RequestDataCollectionResponseDto();
		this.addRequestDataOfPost(requestDataCollection);
		return requestDataCollection;
	}

	private OauthClientDetailsController addRequestDataOfPost(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<OauthClientDetailsRequestDto> requestBody = new RequestBodyResponseDto<OauthClientDetailsRequestDto>(
				OauthClientDetailsRequestDto.class);
		URI uri = linkTo(methodOn(OauthClientDetailsController.class).create(null)).toUri();
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.POST, requestBody);
		return this;
	}

}
