package com.asraf.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.asraf.entities.User;
import com.asraf.services.RoleService;
import com.asraf.services.UserService;

@RestController
@RequestMapping("/roles")
public class RoleController {

	private RoleService roleService;
	private UserService userService;
	private RoleMapper roleMappper;

	@Autowired
	public RoleController(RoleService roleService, UserService userService, RoleMapper roleMappper) {
		this.roleMappper = roleMappper;
		this.roleService = roleService;
		this.userService = userService;
	}

	@GetMapping("")
	public List<RoleResponseDto> getAll() {
		List<RoleResponseDto> response = roleMappper.getResponseDtos(this.roleService.getAll());
		return response;
	}

	@GetMapping("/{id}")
	public RoleResponseDto getById(@PathVariable long id) {
		Role role = roleService.getById(id);
		return roleMappper.getResponseDto(role);
	}

	@GetMapping("/query")
	public Page<RoleResponseDto> getByQuery(@RequestParam String search, Pageable pageable) {
		Page<Role> roles = roleService.getByQuery(search, pageable);
		return roleMappper.getResponseDtos(roles);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public RoleResponseDto create(@Valid @RequestBody RoleRequestDto requestDto) {
		Role role = roleMappper.getEntity(requestDto);
		Set<User> users = new HashSet<>();
		List<Long> idList = requestDto.getUserIds();
		try {
			for (int i = 0; i < idList.size(); i++) {
				long userId = idList.get(i);
				User user = userService.getById(userId);
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		role.setUsers(users);
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
	@ResponseStatus(HttpStatus.CREATED)
	public RoleResponseDto update(@PathVariable long id, @Valid @RequestBody RoleRequestDto requestDto) {
		Role role = roleService.getById(id);
		roleMappper.loadEntity(requestDto, role);
		Set<User> users = role.getUsers();
		List<Long> idList = requestDto.getUserIds();
		try {
			for (int i = 0; i < idList.size(); i++) {
				long userId = idList.get(i);
				User user = userService.getById(userId);
				users.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		role.setUsers(users);
		roleService.save(role);
		return roleMappper.getResponseDto(role);
	}

}
