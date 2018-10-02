package com.asraf.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.asraf.dtos.response.requestdto.RequestBodyResponseDto;
import com.asraf.dtos.response.requestdto.RequestDataCollectionResponseDto;
import com.asraf.entities.Role;
import com.asraf.entities.User;
import com.asraf.entities.UserVerification;
import com.asraf.exceptions.DuplicateResourceFoundException;
import com.asraf.exceptions.ResourceNotFoundException;
import com.asraf.resources.account.AccountResource;
import com.asraf.resources.assemblers.account.AccountResourceAssembler;
import com.asraf.services.RoleService;
import com.asraf.services.UserService;
import com.asraf.services.UserVerificationService;
import com.asraf.services.email.EmailMessageBuilder;
import com.asraf.services.email.EmailSenderService;
import com.asraf.templates.ChangePasswordTemplate;

@RestController
@RequestMapping("/accounts")
public class AccountController extends BaseController {

	private UserService userService;
	private UserMapper userMappper;
	private RoleService roleService;
	private PasswordEncoder userPasswordEncoder;
	private UserDetailsUpdateMapper userDetailsUpdateMapper;
	private EmailSenderService emailSenderService;
	private ChangePasswordTemplate changePasswordTemplate;
	private UserVerificationService userVerificationService;
	private ForgotPasswordMapper forgotPasswordMapper;
	private AccountResourceAssembler accountResourceAssembler;

	@Autowired
	public AccountController(UserService userService, UserMapper userMappper, RoleService roleService,
			PasswordEncoder userPasswordEncoder, UserDetailsUpdateMapper userDetailsUpdateMapper,
			EmailSenderService emailSenderService, ChangePasswordTemplate changePasswordTemplate,
			UserVerificationService userVerificationService, ForgotPasswordMapper forgotPasswordMapper,
			AccountResourceAssembler accountResourceAssembler) {
		this.userMappper = userMappper;
		this.userService = userService;
		this.roleService = roleService;
		this.userPasswordEncoder = userPasswordEncoder;
		this.userDetailsUpdateMapper = userDetailsUpdateMapper;
		this.emailSenderService = emailSenderService;
		this.changePasswordTemplate = changePasswordTemplate;
		this.userVerificationService = userVerificationService;
		this.forgotPasswordMapper = forgotPasswordMapper;
		this.accountResourceAssembler = accountResourceAssembler;
	}

	@GetMapping("")
	public PagedResources<AccountResource> getByQuery(String search, Pageable pageable,
			PagedResourcesAssembler<User> pagedAssembler) {
		Page<User> users = userService.getByQuery(search, pageable);
		return pagedAssembler.toResource(users, this.accountResourceAssembler);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountResource createUser(@Valid @RequestBody UserRequestDto requestDto) {
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
		return accountResourceAssembler.toResource(user);
	}

	@PutMapping("/{id}")
	public AccountResource updateUser(@PathVariable Long id,
			@Valid @RequestBody UserDetailsUpdateRequestDto requestDto) {
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
		return accountResourceAssembler.toResource(user);
	}

	@PostMapping("/forgot-password")
	@ResponseStatus(HttpStatus.CREATED)
	public AccountResource forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto requestDto)
			throws MessagingException, UnsupportedEncodingException {
		User user = userService.getByUsername(requestDto.getUsername());
		UserVerification userVerification = forgotPasswordMapper.getEntity(user);
		userVerificationService.save(userVerification);
		String updatePasswordUrl = linkTo(
				methodOn(this.getClass()).updatePassword(userVerification.getVerificationCode(), null)).toString();
		String callbackUrlWithUpdatePasswordUrl = requestDto.getCallbackUrl() + updatePasswordUrl;
		sendEmail(user, callbackUrlWithUpdatePasswordUrl);
		return accountResourceAssembler.toResource(user);
	}

	@PutMapping("/change-password/{verificationCode}")
	public ResponseEntity<Void> updatePassword(@PathVariable String verificationCode,
			@Valid @RequestBody ChangePasswordRequestDto requestDto) {
		UserVerification userVerification = userVerificationService.getByVerificationCode(verificationCode);
		User user = userService.getById(userVerification.getUser().getId());
		user.setPassword(userPasswordEncoder.encode(requestDto.getPassword()));
		userService.save(user);
		userVerificationService.deleteByUserId(user.getId());
		return null;
	}

	private void sendEmail(User user, String link) throws MessagingException, UnsupportedEncodingException {
		InternetAddress replyTo = new InternetAddress("noreply@auth.com", "no-reply");
		String subject = "Change Password";
		String body = changePasswordTemplate.createTemplate(user, link);
		EmailMessageBuilder emailMessageBuilder = EmailMessageBuilder.builder().emailSubject(subject).emailBody(body)
				.isHtml(true).emailReplyTo(replyTo).emailFrom(null).build()
				.addEmailTo(user.getEmail(), user.getUsername()).buildMail(emailSenderService.getMimeMessage());
		changePasswordTemplate.loadInlineImages(emailMessageBuilder);
		emailSenderService.send();
	}

	@GetMapping("/requests")
	public RequestDataCollectionResponseDto getRequests() throws UnsupportedEncodingException, MessagingException {
		RequestDataCollectionResponseDto requestDataCollection = new RequestDataCollectionResponseDto();
		this.addRequestDataOfPostUser(requestDataCollection).addRequestDataOfPutUser(requestDataCollection)
				.addRequestDataOfPostForgetPassword(requestDataCollection);
		return requestDataCollection;
	}

	private AccountController addRequestDataOfPostUser(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<UserRequestDto> requestBody = new RequestBodyResponseDto<UserRequestDto>(
				UserRequestDto.class);
		URI createUserUri = linkTo(methodOn(AccountController.class).createUser(null)).toUri();
		requestDataCollection.addRequest(createUserUri, org.springframework.http.HttpMethod.POST, requestBody);
		return this;
	}

	private AccountController addRequestDataOfPutUser(RequestDataCollectionResponseDto requestDataCollection) {
		RequestBodyResponseDto<UserDetailsUpdateRequestDto> requestBody = new RequestBodyResponseDto<UserDetailsUpdateRequestDto>(
				UserDetailsUpdateRequestDto.class);
		URI updateUserUri = linkTo(methodOn(AccountController.class).updateUser(null, null)).toUri();
		requestDataCollection.addRequest(updateUserUri, org.springframework.http.HttpMethod.PUT, requestBody);
		return this;
	}

	private AccountController addRequestDataOfPostForgetPassword(RequestDataCollectionResponseDto requestDataCollection)
			throws UnsupportedEncodingException, MessagingException {
		RequestBodyResponseDto<ForgotPasswordRequestDto> requestBody = new RequestBodyResponseDto<ForgotPasswordRequestDto>(
				ForgotPasswordRequestDto.class);
		URI forgetPasswordUri = linkTo(methodOn(AccountController.class).forgotPassword(null)).toUri();
		requestDataCollection.addRequest(forgetPasswordUri, org.springframework.http.HttpMethod.POST, requestBody);
		return this;
	}

	private void checkDuplicateUsername(String userName) {
		try {
			userService.getByUsername(userName);
			throw new DuplicateResourceFoundException(User.class, "username", userName);
		} catch (ResourceNotFoundException e1) {
		}
	}

	private void checkDuplicateEmail(String email) {
		try {
			userService.getByEmail(email);
			throw new DuplicateResourceFoundException(User.class, "email", email);
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
