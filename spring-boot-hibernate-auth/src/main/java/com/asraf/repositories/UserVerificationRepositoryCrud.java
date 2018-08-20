package com.asraf.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.UserVerification;

@Transactional
public interface UserVerificationRepositoryCrud extends PagingAndSortingRepository<UserVerification, Long> {

}
