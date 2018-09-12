package com.asraf.dtos.mapper.account.persistence;

import java.util.Date;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.asraf.dtos.mapper.account.ForgotPasswordMapper;
import com.asraf.dtos.mapper.persistence.DtoMapperImpl;
import com.asraf.entities.User;
import com.asraf.entities.UserVerification;

@Component
@Scope(value = "prototype")
public class ForgotPasswordMapperImpl extends DtoMapperImpl implements ForgotPasswordMapper {

	protected ForgotPasswordMapperImpl(ModelMapper modelMapper) {
		super(modelMapper);
	}

	@Override
	public UserVerification getEntity(User user) {
		UUID uuid = UUID.randomUUID();
		String verificationCode = uuid.toString();
		UserVerification userVerification = new UserVerification();
		userVerification.setUser(user);
		userVerification.setVerificationCode(verificationCode);
		userVerification.setCreationTime(new Date());
		return userVerification;
	}

}
