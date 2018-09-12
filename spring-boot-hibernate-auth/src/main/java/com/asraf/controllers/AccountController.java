package com.asraf.controllers;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
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

import com.asraf.dtos.mapper.UserMapper;
import com.asraf.dtos.mapper.account.ForgotPasswordMapper;
import com.asraf.dtos.mapper.account.UserDetailsUpdateMapper;
import com.asraf.dtos.request.account.ChangePasswordRequestDto;
import com.asraf.dtos.request.account.ForgotPasswordRequestDto;
import com.asraf.dtos.request.account.UserDetailsUpdateRequestDto;
import com.asraf.dtos.request.entities.UserRequestDto;
import com.asraf.dtos.response.entities.UserResponseDto;
import com.asraf.entities.Role;
import com.asraf.entities.User;
import com.asraf.entities.UserVerification;
import com.asraf.exceptions.DuplicateResourceFoundException;
import com.asraf.exceptions.ResourceNotFoundException;
import com.asraf.services.RoleService;
import com.asraf.services.UserService;
import com.asraf.services.UserVerificationService;
import com.asraf.services.email.EmailSenderService;
import com.asraf.services.email.MessageBuilder;
import com.asraf.templates.ChangePasswordTemplate;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private UserService userService;
	private UserMapper userMappper;
	private RoleService roleService;
	private PasswordEncoder userPasswordEncoder;
	private UserDetailsUpdateMapper userDetailsUpdateMapper;
	private EmailSenderService emailSenderService;
	private ChangePasswordTemplate changePasswordTemplate;
	private UserVerificationService userVerificationService;
	private ForgotPasswordMapper forgotPasswordMapper;

	@Autowired
	public AccountController(UserService userService, UserMapper userMappper, RoleService roleService,
			PasswordEncoder userPasswordEncoder, UserDetailsUpdateMapper userDetailsUpdateMapper,
			EmailSenderService emailSenderService, ChangePasswordTemplate changePasswordTemplate,
			UserVerificationService userVerificationService, ForgotPasswordMapper forgotPasswordMapper) {
		this.userMappper = userMappper;
		this.userService = userService;
		this.roleService = roleService;
		this.userPasswordEncoder = userPasswordEncoder;
		this.userDetailsUpdateMapper = userDetailsUpdateMapper;
		this.emailSenderService = emailSenderService;
		this.changePasswordTemplate = changePasswordTemplate;
		this.userVerificationService = userVerificationService;
		this.forgotPasswordMapper = forgotPasswordMapper;
	}

	@GetMapping("")
	public List<UserResponseDto> getAll() {
		List<UserResponseDto> response = userMappper.getResponseDtos(this.userService.getAll());
		return response;
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(@Valid @RequestBody UserRequestDto requestDto) throws Exception {
		User user = userMappper.getEntity(requestDto);
		checkDuplicateUsername(requestDto.getUsername());
		checkDuplicateEmail(requestDto.getEmail());
		user.setPassword(userPasswordEncoder.encode(requestDto.getPassword()));
		user.setCreationTime(new Date());
		user.setUpdateTime(new Date());
		List<Long> roleIdList = requestDto.getRoleIds();
		Set<Role> roles = addRoles(user, roleIdList);
		user.setRoles(roles);
		userService.save(user);
		return;
	}

	@PutMapping("/{id}")
	public void updateUser(@PathVariable long id, @Valid @RequestBody UserDetailsUpdateRequestDto requestDto)
			throws Exception {
		User user = userService.getById(id);
		user.getRoles().size();
		if (!user.getUsername().equals(requestDto.getUsername())) {
			checkDuplicateUsername(requestDto.getUsername());
		}
		if (!user.getEmail().equals(requestDto.getEmail())) {
			checkDuplicateEmail(requestDto.getEmail());
		}
		userDetailsUpdateMapper.loadEntity(requestDto, user);
		user.setUpdateTime(new Date());
		List<Long> roleIdList = requestDto.getRoleIds();
		Set<Role> roles = addRoles(user, roleIdList);
		user.setRoles(roles);
		userService.save(user);
		return;
	}

	@PostMapping("/forgot-password")
	@ResponseStatus(HttpStatus.CREATED)
	public void forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto requestDto) throws MessagingException {
		User user = userService.getByUsername(requestDto.getUsername());
		UserVerification userVerification = forgotPasswordMapper.getEntity(user);
		userVerificationService.save(userVerification);
		String link = "http://localhost:8081/accounts/change-password/" + userVerification.getVerificationCode();
		sendEmail(user, link);
		return;
	}

	@PutMapping("/change-password/{verificationCode}")
	public void updatePassword(@PathVariable String verificationCode,
			@Valid @RequestBody ChangePasswordRequestDto requestDto) {
		UserVerification userVerification = userVerificationService.getByVerificationCode(verificationCode);
		User user = userService.getById(userVerification.getUser().getId());
		user.setPassword(userPasswordEncoder.encode(requestDto.getPassword()));
		userService.save(user);
		userVerificationService.deleteByUserId(user.getId());
		return;
	}

	private void sendEmail(User user, String link) throws MessagingException {
		MessageBuilder messageBuilder = MessageBuilder.builder().emailTo(user.getEmail())
				.emailBody(changePasswordTemplate.createTemplate(user, link)).emailSubject("Change Password").build();
		emailSenderService.sendHtml(messageBuilder);
	}

	private void checkDuplicateUsername(String userName) {
		try {
			userService.getByUsername(userName);
			throw new DuplicateResourceFoundException(getClass(), "Duplicate Username ", userName);
		} catch (ResourceNotFoundException e1) {
		}
	}

	private void checkDuplicateEmail(String email) {
		try {
			userService.getByEmail(email);
			throw new DuplicateResourceFoundException(getClass(), "Duplicate Email", email);
		} catch (ResourceNotFoundException e1) {
		}
	}

	private Set<Role> addRoles(User user, List<Long> roleIdList) {
		Set<Role> roles = user.getRoles();
		try {
			for (int i = 0; i < roleIdList.size(); i++) {
				long roleId = roleIdList.get(i);
				Role role = roleService.getById(roleId);
				roles.add(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}

}
