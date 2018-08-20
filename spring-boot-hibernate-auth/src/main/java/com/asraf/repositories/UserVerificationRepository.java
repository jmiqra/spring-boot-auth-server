package com.asraf.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.asraf.entities.UserVerification;

@Transactional
public interface UserVerificationRepository
		extends UserVerificationRepositoryCrud, JpaSpecificationExecutor<UserVerification> {

}
