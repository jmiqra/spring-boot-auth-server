package com.asraf.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asraf.entities.UserVerification;

public interface UserVerificationService {

	UserVerification save(UserVerification userVerification);

	void delete(UserVerification userVerification);

	UserVerification getById(Long id);

	Iterable<UserVerification> getAll();

	Page<UserVerification> getByQuery(String search, Pageable pageable);

}
