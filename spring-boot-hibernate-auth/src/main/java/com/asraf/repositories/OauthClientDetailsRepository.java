package com.asraf.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.asraf.entities.OauthClientDetails;

@Transactional
public interface OauthClientDetailsRepository
		extends OauthClientDetailsRepositoryCrud, JpaSpecificationExecutor<OauthClientDetails> {

}
