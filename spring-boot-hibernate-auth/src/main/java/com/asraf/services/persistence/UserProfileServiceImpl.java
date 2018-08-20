package com.asraf.services.persistence;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.entities.UserProfile;
import com.asraf.repositories.UserProfileRepository;
import com.asraf.services.UserProfileService;
import com.asraf.utils.ExceptionPreconditions;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

	private UserProfileRepository userProfileRepository;

	@Autowired
	public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
		this.userProfileRepository = userProfileRepository;
	}

	public UserProfile save(UserProfile userProfile) {
		return userProfileRepository.save(userProfile);
	}

	public void delete(UserProfile userProfile) {
		userProfileRepository.delete(userProfile);
	}

	public UserProfile getById(Long id) {
		try {
			return userProfileRepository.findById(id).get();
		} catch (NoSuchElementException nseex) {
			return ExceptionPreconditions.entityNotFound(UserProfile.class, "id", id.toString());
		}
	}

	public Iterable<UserProfile> getAll() {
		return userProfileRepository.findAll();
	}

}
