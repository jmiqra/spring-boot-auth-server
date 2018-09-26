package com.asraf.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.asraf.entities.UserClaim;

@Transactional
public interface UserClaimRepository extends UserClaimRepositoryCrud, JpaSpecificationExecutor<UserClaim> {

}
