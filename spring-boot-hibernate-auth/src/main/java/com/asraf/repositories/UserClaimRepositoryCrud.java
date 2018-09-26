package com.asraf.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.UserClaim;

@Transactional
public interface UserClaimRepositoryCrud extends PagingAndSortingRepository<UserClaim, Long> {

}
