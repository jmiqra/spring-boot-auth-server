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

import com.asraf.dtos.mapper.RoleMapper;
import com.asraf.dtos.request.entities.RoleRequestDto;
import com.asraf.dtos.response.entities.RoleResponseDto;
import com.asraf.entities.Role;
import com.asraf.services.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {

	private RoleService roleService;
	private RoleMapper roleMappper;

	@Autowired
	public RoleController(RoleService roleService, RoleMapper roleMappper) {
		this.roleMappper = roleMappper;
		this.roleService = roleService;
	}

	@GetMapping("")
	public List<RoleResponseDto> getAll() {
		List<RoleResponseDto> response = roleMappper.getResponseDtos(this.roleService.getAll());
		return response;
	}

	@GetMapping("/me")
	public Principal role(Principal principal) {
		return principal;
	}

	/**
	 * @SampleUrl /roles/query?search=(name==rats*;id>1,name==ratul);id=in=(2,3,4,5,6)&page=0&size=2&sort=name,asc&sort=email,desc
	 * @param search
	 * @param pageable
	 * @return
	 */
	@GetMapping("/query")
	public Page<RoleResponseDto> getByQuery(@RequestParam String search, Pageable pageable) {
		Page<Role> roles = roleService.getByQuery(search, pageable);
		return roleMappper.getResponseDtos(roles);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public RoleResponseDto create(@Valid @RequestBody RoleRequestDto requestDto) {
		Role role = roleMappper.getEntity(requestDto);
		roleService.save(role);
		return roleMappper.getResponseDto(role);
	}

	@DeleteMapping("/{id}")
	public RoleResponseDto delete(@PathVariable long id) {
		Role role = roleService.getById(id);
		RoleResponseDto response = roleMappper.getResponseDto(role);
		roleService.delete(role);
		return response;
	}

	@PutMapping("/{id}")
	public RoleResponseDto update(@PathVariable long id, @Valid @RequestBody RoleRequestDto requestDto) {
		Role role = roleService.getById(id);
		roleMappper.loadEntity(requestDto, role);
		roleService.save(role);
		return roleMappper.getResponseDto(role);
	}

}
