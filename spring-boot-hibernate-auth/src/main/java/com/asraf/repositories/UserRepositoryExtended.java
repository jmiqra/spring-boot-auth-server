package com.asraf.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.asraf.constants.ColumnType;
import com.asraf.entities.User;
import com.asraf.models.search.extended.UserWithVerificationSearch;

@Transactional
public interface UserRepositoryExtended {

	Page<User> GetByUserWithVerificationSeach(UserWithVerificationSearch searchItem, Pageable pageable);

	List<User> getByName(UserWithVerificationSearch searchItem);

	Page<Object> getByDistinctColumn(String columnName, ColumnType columnType, Pageable pageable);

}
