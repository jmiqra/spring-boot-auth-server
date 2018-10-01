package com.asraf.resources.assemblers.account;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.asraf.controllers.AccountController;
import com.asraf.dtos.mapper.AccountUserMapper;
import com.asraf.entities.User;
import com.asraf.resources.account.AccountResource;
import com.asraf.resources.assemblers.BaseResourceAssembler;

@Component
public class AccountResourceAssembler extends BaseResourceAssembler<User, AccountResource> {

	private final AccountUserMapper accountUserMapper;

	@Autowired
	public AccountResourceAssembler(AccountUserMapper accountUserMapper) {
		super(AccountController.class, AccountResource.class);
		this.accountUserMapper = accountUserMapper;
	}

	@Override
	public AccountResource toResource(User entity) {
		try {
			return new AccountResource(entity, accountUserMapper);
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
		return null;
	}

}