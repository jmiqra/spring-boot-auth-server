package com.asraf.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.asraf.dtos.mapper.UserMapper;
import com.asraf.dtos.request.entities.UserRequestDto;
import com.asraf.dtos.response.requestdto.RequestBodyResponseDto;
import com.asraf.dtos.response.requestdto.RequestDataCollectionResponseDto;
import com.asraf.entities.Role;
import com.asraf.entities.User;
import com.asraf.resources.assemblers.entities.UserResourceAssembler;
import com.asraf.resources.entities.UserResource;
import com.asraf.services.RoleService;
import com.asraf.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

	private UserService userService;
	private UserMapper userMappper;
	private RoleService roleService;
	private UserResourceAssembler userResourceAssembler;

	@Autowired
	public UserController(UserService userService, UserMapper userMappper, RoleService roleService,
			UserResourceAssembler userResourceAssembler) {
		this.userMappper = userMappper;
		this.userService = userService;
		this.roleService = roleService;
		this.userResourceAssembler = userResourceAssembler;
	}

	@PreAuthorize("hasPermission('show-who-am-i,show-my-principal', 'true,true')")
	@GetMapping("/me")
	public Principal user(Principal principal) {
		return principal;
	}

	@GetMapping("/{id}")
	public UserResource getById(@PathVariable long id) {
		User user = userService.getById(id);
		return this.userResourceAssembler.toResource(user);
	}

	@GetMapping("/get-by-email/{email}")
	public UserResource getByEmail(@PathVariable String email) {
		User user = userService.getByEmail(email);
		return this.userResourceAssembler.toResource(user);
	}

	// @GetMapping("/get-by-name/{name}")
	// public List<UserResponseDto> getByName(@PathVariable String name) {
	// List<User> users = userService.getByNameContains(name);
	// return userMappper.getResponseDtos(users);
	// }
	//
	// @GetMapping("/search-crud")
	// public List<UserResponseDto> getBySearchCrud(UserSearch searchItem) {
	// List<User> users = userService.getBySearchCrud(searchItem);
	// return userMappper.getResponseDtos(users);
	// }
	//
	// @GetMapping("/search-crud-pageable")
	// public Page<UserResponseDto> getBySearchCrudPageable(UserSearch searchItem,
	// Pageable pageable) {
	// Page<User> pagedUser = userService.getBySearchCrudPageable(searchItem,
	// pageable);
	// return userMappper.getResponseDtos(pagedUser);
	// }

	@GetMapping("")
	public PagedResources<UserResource> getByQuery(String search, Pageable pageable,
			PagedResourcesAssembler<User> pagedAssembler) {
		Page<User> users = userService.getByQuery(search, pageable);
		return pagedAssembler.toResource(users, this.userResourceAssembler);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public UserResource create(@Valid @RequestBody UserRequestDto requestDto) {
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
		return this.userResourceAssembler.toResource(user);
	}

	@DeleteMapping("/{id}")
	public UserResource delete(@PathVariable long id) {
		User user = userService.getById(id);
		UserResource response = this.userResourceAssembler.toResource(user).forDeletion();
		userService.delete(user);
		return response;
	}

	@PutMapping("/{id}")
	public UserResource update(@PathVariable long id, @Valid @RequestBody UserRequestDto requestDto) {
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
		return this.userResourceAssembler.toResource(user);
	}

	@GetMapping("/requests")
	public RequestDataCollectionResponseDto getRequests() {
		RequestDataCollectionResponseDto requestDataCollection = new RequestDataCollectionResponseDto();
		this.addRequestDataOfPost(requestDataCollection);
		return requestDataCollection;
	}

	private UserController addRequestDataOfPost(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<UserRequestDto> requestBody = new RequestBodyResponseDto<UserRequestDto>(
				UserRequestDto.class);
		URI uri = linkTo(methodOn(UserController.class).create(null)).toUri();
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.POST, requestBody);
		return this;
	}

}
