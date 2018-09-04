package com.asraf.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.asraf.dtos.mapper.OauthClientDetailsMapper;
import com.asraf.dtos.request.entities.OauthClientDetailsRequestDto;
import com.asraf.dtos.response.entities.OauthClientDetailsResponseDto;
import com.asraf.entities.OauthClientDetails;
import com.asraf.services.OauthClientDetailsService;

@RestController
@RequestMapping("/oauth-clients")
public class OauthClientDetailsController {

	private OauthClientDetailsService oauthClientDetailsService;
	private OauthClientDetailsMapper oauthClientDetailsMappper;

	@Autowired
	public OauthClientDetailsController(OauthClientDetailsService oauthClientDetailsService,
			OauthClientDetailsMapper oauthClientDetailsMappper) {
		this.oauthClientDetailsMappper = oauthClientDetailsMappper;
		this.oauthClientDetailsService = oauthClientDetailsService;
	}

	@GetMapping("")
	public List<OauthClientDetailsResponseDto> getAll() {
		List<OauthClientDetailsResponseDto> response = oauthClientDetailsMappper
				.getResponseDtos(this.oauthClientDetailsService.getAll());
		return response;
	}

	@GetMapping("/me")
	public Principal oauthClientDetails(Principal principal) {
		return principal;
	}

	/**
	 * @SampleUrl /oauthClientDetailss/query?search=(name==rats*;id>1,name==ratul);id=in=(2,3,4,5,6)&page=0&size=2&sort=name,asc&sort=email,desc
	 * @param search
	 * @param pageable
	 * @return
	 */
	@GetMapping("/query")
	public Page<OauthClientDetailsResponseDto> getByQuery(@RequestParam String search, Pageable pageable) {
		Page<OauthClientDetails> oauthClientDetailss = oauthClientDetailsService.getByQuery(search, pageable);
		return oauthClientDetailsMappper.getResponseDtos(oauthClientDetailss);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public OauthClientDetailsResponseDto create(@Valid @RequestBody OauthClientDetailsRequestDto requestDto) {
		OauthClientDetails oauthClientDetails = oauthClientDetailsMappper.getEntity(requestDto);
		oauthClientDetailsService.save(oauthClientDetails);
		return oauthClientDetailsMappper.getResponseDto(oauthClientDetails);
	}

	@DeleteMapping("/{id}")
	public OauthClientDetailsResponseDto delete(@PathVariable String id) {
		OauthClientDetails oauthClientDetails = oauthClientDetailsService.getById(id);
		OauthClientDetailsResponseDto response = oauthClientDetailsMappper.getResponseDto(oauthClientDetails);
		oauthClientDetailsService.delete(oauthClientDetails);
		return response;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public OauthClientDetailsResponseDto update(@PathVariable String id,
			@Valid @RequestBody OauthClientDetailsRequestDto requestDto) {
		OauthClientDetails oauthClientDetails = oauthClientDetailsService.getById(id);
		oauthClientDetailsMappper.loadEntity(requestDto, oauthClientDetails);
		oauthClientDetailsService.save(oauthClientDetails);
		return oauthClientDetailsMappper.getResponseDto(oauthClientDetails);
	}

}
