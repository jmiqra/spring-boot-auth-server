package com.asraf.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asraf.entities.Role;

public interface RoleService {

	Role save(Role role);

	void delete(Role role);

	Role getById(Long id);

	Iterable<Role> getAll();

	Page<Role> getByQuery(String search, Pageable pageable);

}
