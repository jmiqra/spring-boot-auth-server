package com.asraf.repositories;

import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.User;

@Transactional
public interface UserRepositoryQdsl extends ExtendedQueryDslJpaRepository<User, Long>  {

}
