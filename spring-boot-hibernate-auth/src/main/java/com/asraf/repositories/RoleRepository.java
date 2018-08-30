package com.asraf.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.asraf.entities.Role;

@Transactional
public interface RoleRepository extends RoleRepositoryCrud, JpaSpecificationExecutor<Role> {

}
