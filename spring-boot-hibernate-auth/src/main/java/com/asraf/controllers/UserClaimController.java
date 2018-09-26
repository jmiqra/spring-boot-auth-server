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

import com.asraf.dtos.mapper.UserClaimMapper;
import com.asraf.dtos.request.entities.UserClaimRequestDto;
import com.asraf.dtos.response.entities.UserClaimResponseDto;
import com.asraf.entities.UserClaim;
import com.asraf.services.UserClaimService;

@RestController
@RequestMapping("/user-claims")
public class UserClaimController {

	private UserClaimService userClaimService;
	private UserClaimMapper userClaimMappper;

	@Autowired
	public UserClaimController(UserClaimService userClaimService, UserClaimMapper userClaimMappper) {
		this.userClaimMappper = userClaimMappper;
		this.userClaimService = userClaimService;
	}

	@GetMapping("")
	public List<UserClaimResponseDto> getAll() {
		List<UserClaimResponseDto> response = userClaimMappper.getResponseDtos(this.userClaimService.getAll());
		return response;
	}

	@GetMapping("/me")
	public Principal userClaim(Principal principal) {
		return principal;
	}

	/**
	 * @SampleUrl /userClaims/query?search=(name==rats*;id>1,name==ratul);id=in=(2,3,4,5,6)&page=0&size=2&sort=name,asc&sort=email,desc
	 * @param search
	 * @param pageable
	 * @return
	 */
	@GetMapping("/query")
	public Page<UserClaimResponseDto> getByQuery(@RequestParam String search, Pageable pageable) {
		Page<UserClaim> userClaims = userClaimService.getByQuery(search, pageable);
		return userClaimMappper.getResponseDtos(userClaims);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public UserClaimResponseDto create(@Valid @RequestBody UserClaimRequestDto requestDto) {
		UserClaim userClaim = userClaimMappper.getEntity(requestDto);
		userClaimService.save(userClaim);
		return userClaimMappper.getResponseDto(userClaim);
	}

	@DeleteMapping("/{id}")
	public UserClaimResponseDto delete(@PathVariable long id) {
		UserClaim userClaim = userClaimService.getById(id);
		UserClaimResponseDto response = userClaimMappper.getResponseDto(userClaim);
		userClaimService.delete(userClaim);
		return response;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public UserClaimResponseDto update(@PathVariable long id, @Valid @RequestBody UserClaimRequestDto requestDto) {
		UserClaim userClaim = userClaimService.getById(id);
		userClaimMappper.loadEntity(requestDto, userClaim);
		userClaimService.save(userClaim);
		return userClaimMappper.getResponseDto(userClaim);
	}

}
