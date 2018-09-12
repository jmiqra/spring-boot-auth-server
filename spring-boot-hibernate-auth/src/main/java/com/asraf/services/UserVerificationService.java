package com.asraf.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asraf.entities.UserVerification;

public interface UserVerificationService {

	UserVerification save(UserVerification userVerification);

	void delete(UserVerification userVerification);
	
	void deleteByUserId(long userId);

	UserVerification getById(Long id);
	
	UserVerification getByVerificationCode(String verificationCode);

	Iterable<UserVerification> getAll();

	Page<UserVerification> getByQuery(String search, Pageable pageable);

}
