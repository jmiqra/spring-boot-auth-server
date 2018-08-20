package com.asraf.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.User;

@Transactional
public interface UserRepositoryCrud extends PagingAndSortingRepository<User, Long> {

	/**
	 * Retrieves an user by its email.
	 * 
	 * @param email
	 * @return The user having the passed email or null if no user is found
	 */
	public User findByEmail(String email);

	@Query("select u from User u where u.name like %?1% order by name")
	List<User> findByNameContains(String name);

	List<User> findByNameOrEmail(String name, String email);

	// Slice<User> findAll(Pageable pageRequest);
	Page<User> findByNameContainsOrEmailContainsAllIgnoreCase(String name, String email, Pageable pageRequest);

	// @Query("SELECT t FROM Todo t WHERE " + "LOWER(t.title) LIKE
	// LOWER(CONCAT('%',:searchTerm, '%')) OR "
	// + "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	// List<Todo> findBySearchTerm(@Param("searchTerm") String searchTerm, Pageable
	// pageRequest);

}
