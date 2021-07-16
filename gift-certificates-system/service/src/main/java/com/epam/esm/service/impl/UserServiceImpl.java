package com.epam.esm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.SecurityUser;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.MessageKey;
import com.epam.esm.validator.UserValidator;

/**
 * Class is implementation of interface {@link UserService} and intended to work
 * with user
 *
 * @author Aleksandra Podgayskaya
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final ModelMapper modelMapper;
	private final UserValidator userValidator;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserDao userDao, ModelMapper modelMapper, UserValidator userValidator,
			@Lazy PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.modelMapper = modelMapper;
		this.userValidator = userValidator;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO validate
		checkUniquenessEmail(userDto.getEmail());
		userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userDto.setRole(Role.USER);
		User createdUser = userDao.create(modelMapper.map(userDto, User.class));
		return modelMapper.map(createdUser, UserDto.class);
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

	/*@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO validate
		System.out.println("GO TO LOAD-USER");//TODO
		Optional<User> foundUser = userDao.findByEmail(email);
		System.out.println("LOAD-USER" + foundUser.get().toString());// TODO
		if (foundUser.isEmpty()) {
			throw new UsernameNotFoundException("user doesn't exists");
		}
		User user = foundUser.get();
		return new SecurityUser(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRole());
	}*/

	private void checkUniquenessEmail(String email) {
		Optional<User> userOptional = userDao.findByEmail(email);
		if (userOptional.isPresent()) {
			Map<String, String> incorrectParameter = new HashMap<>();
			incorrectParameter.put(MessageKey.PARAMETER_REPEATED_EMAIL, email);
			throw new IncorrectParameterValueException("repeated user email", incorrectParameter,
					ErrorCode.USER.getCode());
		}
	}
}
