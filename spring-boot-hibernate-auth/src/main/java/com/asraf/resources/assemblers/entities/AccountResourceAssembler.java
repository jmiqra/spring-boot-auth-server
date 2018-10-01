package com.asraf.resources.assemblers.entities;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asraf.controllers.AccountController;
import com.asraf.dtos.mapper.UserMapper;
import com.asraf.entities.User;
import com.asraf.resources.assemblers.BaseResourceAssembler;
import com.asraf.resources.entities.AccountResource;

@Component
public class AccountResourceAssembler extends BaseResourceAssembler<User, AccountResource> {

	private final UserMapper userMapper;

	@Autowired
	public AccountResourceAssembler(UserMapper userMapper) {
		super(AccountController.class, AccountResource.class);
		this.userMapper = userMapper;
	}

	@Override
	public AccountResource toResource(User entity) {
		try {
			return new AccountResource(entity, userMapper);
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

}