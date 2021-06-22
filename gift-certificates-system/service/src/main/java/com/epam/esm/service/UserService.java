package com.epam.esm.service;

import java.util.List;

import com.epam.esm.dto.UserDto;

public interface UserService {

	List<UserDto> findAllUsers();

	UserDto findUserById(long id);

}
