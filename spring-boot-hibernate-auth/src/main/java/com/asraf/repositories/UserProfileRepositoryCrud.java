package com.asraf.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.UserProfile;

@Transactional
public interface UserProfileRepositoryCrud extends PagingAndSortingRepository<UserProfile, Long> {

}
