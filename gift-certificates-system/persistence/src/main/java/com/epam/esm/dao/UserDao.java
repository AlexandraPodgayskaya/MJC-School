package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;

public interface UserDao {

	List<User> findAll(Pagination pagination);

	Optional<User> findById(long id);
	
	public long getTotalNumber();

}
