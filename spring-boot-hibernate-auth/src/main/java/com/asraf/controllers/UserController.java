package com.asraf.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.asraf.dtos.mapper.UserMapper;
import com.asraf.dtos.request.entities.UserRequestDto;
import com.asraf.dtos.response.entities.UserResponseDto;
import com.asraf.entities.Role;
import com.asraf.entities.User;
import com.asraf.models.search.UserSearch;
import com.asraf.services.RoleService;
import com.asraf.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;
	private UserMapper userMappper;
	private RoleService roleService;

	@Autowired
	public UserController(UserService userService, UserMapper userMappper, RoleService roleService) {
		this.userMappper = userMappper;
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping("")
	public List<UserResponseDto> getAll() {
		List<UserResponseDto> response = userMappper.getResponseDtos(this.userService.getAll());
		return response;
	}

	@PreAuthorize("hasPermission('show-who-am-i,show-my-principal', 'true,true')")
	@GetMapping("/me")
	public Principal user(Principal principal) {
		return principal;
	}

	@GetMapping("/get-by-email/{email}")
	public UserResponseDto getByEmail(@PathVariable String email) {
		User user = userService.getByEmail(email);
		return userMappper.getResponseDto(user);
	}

	@GetMapping("/get-by-name/{name}")
	public List<UserResponseDto> getByName(@PathVariable String name) {
		List<User> users = userService.getByNameContains(name);
		return userMappper.getResponseDtos(users);
	}

	@GetMapping("/search-crud")
	public List<UserResponseDto> getBySearchCrud(UserSearch searchItem) {
		List<User> users = userService.getBySearchCrud(searchItem);
		return userMappper.getResponseDtos(users);
	}

	@GetMapping("/search-crud-pageable")
	public Page<UserResponseDto> getBySearchCrudPageable(UserSearch searchItem, Pageable pageable) {
		Page<User> pagedUser = userService.getBySearchCrudPageable(searchItem, pageable);
		return userMappper.getResponseDtos(pagedUser);
	}

	@GetMapping("/query")
	public Page<UserResponseDto> getByQuery(@RequestParam String search, Pageable pageable) {
		Page<User> users = userService.getByQuery(search, pageable);
		return userMappper.getResponseDtos(users);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResponseDto create(@Valid @RequestBody UserRequestDto requestDto) {
		User user = userMappper.getEntity(requestDto);
		Set<Role> roles = user.getRoles();
		List<Long> idList = requestDto.getRoleIds();
		try {
			for (int i = 0; i < idList.size(); i++) {
				long roleId = idList.get(i);
				Role role = roleService.getById(roleId);
				roles.add(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setRoles(roles);
		userService.save(user);
		return userMappper.getResponseDto(user);
	}

	@DeleteMapping("/{id}")
	public UserResponseDto delete(@PathVariable long id) {
		User user = userService.getById(id);
		UserResponseDto response = userMappper.getResponseDto(user);
		userService.delete(user);
		return response;
	}

	@PutMapping("/{id}")
	public UserResponseDto update(@PathVariable long id, @Valid @RequestBody UserRequestDto requestDto) {
		User user = userService.getById(id);
		userMappper.loadEntity(requestDto, user);
		Set<Role> roles = user.getRoles();
		List<Long> idList = requestDto.getRoleIds();
		try {
			for (int i = 0; i < idList.size(); i++) {
				long roleId = idList.get(i);
				Role role = roleService.getById(roleId);
				roles.add(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setRoles(roles);
		userService.save(user);
		return userMappper.getResponseDto(user);
	}

}
