package com.epam.esm.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.MessageKey;
import com.epam.esm.validator.UserValidator;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final ModelMapper modelMapper;
	private final UserValidator userValidator;

	@Autowired
	public UserServiceImpl(UserDao userDao, ModelMapper modelMapper, UserValidator userValidator) {
		this.userDao = userDao;
		this.modelMapper = modelMapper;
		this.userValidator = userValidator;
	}

	@Override
	public PageDto<UserDto> findAllUsers(PaginationDto paginationDto) {
		Pagination pagination = modelMapper.map(paginationDto, Pagination.class);
		List<User> foundUsers = userDao.findAll(pagination);
		List<UserDto> foundUsersDto = foundUsers.stream().map(foundUser -> modelMapper.map(foundUser, UserDto.class))
				.collect(Collectors.toList());
		long totalNumberPositions = userDao.getTotalNumber();
		PageDto<UserDto> usersPage = new PageDto<>(foundUsersDto, totalNumberPositions);
		return usersPage;
	}

	@Override
	public UserDto findUserById(long id) throws IncorrectParameterValueException, ResourceNotFoundException {
		userValidator.validateId(id);
		Optional<User> foundUser = userDao.findById(id);
		return foundUser.map(user -> modelMapper.map(user, UserDto.class))
				.orElseThrow(() -> new ResourceNotFoundException("no user by id", MessageKey.USER_NOT_FOUND_BY_ID,
						String.valueOf(id), ErrorCode.USER.getCode()));
	}
}
