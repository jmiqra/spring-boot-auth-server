package com.asraf.controllers;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.asraf.dtos.mapper.UserDetailsUpdateMapper;
import com.asraf.dtos.mapper.UserMapper;
import com.asraf.dtos.request.entities.ChangePasswordRequestDto;
import com.asraf.dtos.request.entities.UserDetailsUpdateRequestDto;
import com.asraf.dtos.request.entities.UserRequestDto;
import com.asraf.dtos.response.entities.UserResponseDto;
import com.asraf.entities.Role;
import com.asraf.entities.User;
import com.asraf.services.RoleService;
import com.asraf.services.UserService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private UserService userService;
	private UserMapper userMappper;
	private RoleService roleService;
	private PasswordEncoder userPasswordEncoder;
	private UserDetailsUpdateMapper userDetailsUpdateMapper;

	@Autowired
	public AccountController(UserService userService, UserMapper userMappper, RoleService roleService,
			PasswordEncoder userPasswordEncoder, UserDetailsUpdateMapper userDetailsUpdateMapper) {
		this.userMappper = userMappper;
		this.userService = userService;
		this.roleService = roleService;
		this.userPasswordEncoder = userPasswordEncoder;
		this.userDetailsUpdateMapper = userDetailsUpdateMapper;
	}

	@GetMapping("")
	public List<UserResponseDto> getAll() {
		List<UserResponseDto> response = userMappper.getResponseDtos(this.userService.getAll());
		return response;
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public String create(@Valid @RequestBody UserRequestDto requestDto) {
		User user = userMappper.getEntity(requestDto);
		//do
		User userTemp = userService.getByUsername(requestDto.getUsername());
		if(userTemp == null)
		{
			System.out.println("User name doesn't exist");
		}
		//do
		User userTemp1 = userService.getByEmail(requestDto.getEmail());
		if(userTemp1 == null)
		{
			System.out.println("User email doesn't exist");
		}
		user.setPassword(userPasswordEncoder.encode(requestDto.getPassword()));
		Date date = new Date();
		user.setCreationTime(date);
		user.setUpdateTime(date);
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
		return "User Sign Up Completed";
	}

	@PutMapping("/{id}")
	public String update(@PathVariable long id, @Valid @RequestBody UserDetailsUpdateRequestDto requestDto) {
		User user = userService.getById(id);
		userDetailsUpdateMapper.loadEntity(requestDto, user);
		Date date = new Date();
		user.setUpdateTime(date);
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
		return "User Details update Completed";
	}
	
	@PutMapping("/{id}/change-password")
	public String updatePassword(@PathVariable long id, @Valid @RequestBody ChangePasswordRequestDto requestDto) {
		User user = userService.getById(id);
		user.setPassword(userPasswordEncoder.encode(requestDto.getPassword()));
		userService.save(user);
		return "User Password Change Completed";
	}

}
