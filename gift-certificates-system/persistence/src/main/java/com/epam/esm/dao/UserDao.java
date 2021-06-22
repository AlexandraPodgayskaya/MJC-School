package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

import com.epam.esm.entity.User;

public interface UserDao {

	List<User> findAll();

	Optional<User> findById(long id);

}
