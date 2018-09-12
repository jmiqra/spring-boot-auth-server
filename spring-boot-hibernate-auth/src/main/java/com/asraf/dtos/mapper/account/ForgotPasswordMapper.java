package com.asraf.dtos.mapper.account;

import com.asraf.dtos.mapper.DtoMapper;
import com.asraf.entities.User;
import com.asraf.entities.UserVerification;

public interface ForgotPasswordMapper extends DtoMapper {
	UserVerification getEntity(User user);
}
