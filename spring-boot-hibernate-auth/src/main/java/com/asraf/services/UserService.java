package com.asraf.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asraf.entities.User;
import com.asraf.models.search.UserSearch;

public interface UserService {

	User save(User user);

	void delete(User user);

	User getById(Long id);

	Iterable<User> getAll();

	User getByEmail(String email);

	List<User> getByNameContains(String name);

	List<User> getBySearchCrud(UserSearch searchItem);

	Page<User> getBySearchCrudPageable(UserSearch searchItem, Pageable pageable);

	Page<User> getByQuery(String search, Pageable pageable);

}
