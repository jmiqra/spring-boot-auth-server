package com.asraf.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.asraf.dtos.mapper.RoleMapper;
import com.asraf.dtos.request.entities.RoleRequestDto;
import com.asraf.dtos.response.requestdto.RequestBodyResponseDto;
import com.asraf.dtos.response.requestdto.RequestDataCollectionResponseDto;
import com.asraf.entities.Role;
import com.asraf.entities.User;
import com.asraf.resources.assemblers.entities.RoleResourceAssembler;
import com.asraf.resources.entities.RoleResource;
import com.asraf.services.RoleService;
import com.asraf.services.UserService;

@RestController
@RequestMapping("/roles")
public class RoleController extends BaseController {

	private RoleService roleService;
	private UserService userService;
	private RoleMapper roleMappper;
	private RoleResourceAssembler roleResourceAssembler;

	@Autowired
	public RoleController(RoleService roleService, UserService userService, RoleMapper roleMappper,
			RoleResourceAssembler roleResourceAssembler) {
		this.roleMappper = roleMappper;
		this.roleService = roleService;
		this.userService = userService;
		this.roleResourceAssembler = roleResourceAssembler;
	}

	@GetMapping("")
	public PagedResources<RoleResource> getByQuery(String search, Pageable pageable,
			PagedResourcesAssembler<Role> pagedAssembler) {
		Page<Role> roles = roleService.getByQuery(search, pageable);
		return pagedAssembler.toResource(roles, this.roleResourceAssembler);
	}

	@GetMapping("/{id}")
	public RoleResource getById(@PathVariable long id) {
		Role role = roleService.getById(id);
		return this.roleResourceAssembler.toResource(role);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public RoleResource create(@Valid @RequestBody RoleRequestDto requestDto) {
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
		return this.roleResourceAssembler.toResource(role);
	}

	@DeleteMapping("/{id}")
	public RoleResource delete(@PathVariable long id) {
		Role role = roleService.getById(id);
		RoleResource response = this.roleResourceAssembler.toResource(role).forDeletion();
		roleService.delete(role);
		return response;
	}

	@PutMapping("/{id}")
	public RoleResource update(@PathVariable long id, @Valid @RequestBody RoleRequestDto requestDto) {
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
		return this.roleResourceAssembler.toResource(role);
	}

	@GetMapping("/requests")
	public RequestDataCollectionResponseDto getRequests() {
		RequestDataCollectionResponseDto requestDataCollection = new RequestDataCollectionResponseDto();
		this.addRequestDataOfPost(requestDataCollection);
		return requestDataCollection;
	}

	private RoleController addRequestDataOfPost(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<RoleRequestDto> requestBody = new RequestBodyResponseDto<RoleRequestDto>(
				RoleRequestDto.class);
		URI uri = linkTo(methodOn(RoleController.class).create(null)).toUri();
		requestDataCollection.addRequest(uri, org.springframework.http.HttpMethod.POST, requestBody);
		return this;
	}

}
