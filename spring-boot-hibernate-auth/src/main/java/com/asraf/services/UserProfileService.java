package com.asraf.services;

import com.asraf.entities.UserProfile;

public interface UserProfileService {

	UserProfile save(UserProfile userProfile);

	void delete(UserProfile userProfile);

	UserProfile getById(Long id);

	Iterable<UserProfile> getAll();

}
