package com.epam.esm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.converter.ParametersToDtoConverter;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	private final ParametersToDtoConverter parametersToDtoConverter;

	@Autowired
	public UserController(UserService userService, ParametersToDtoConverter parametersToDtoConverter) {
		this.userService = userService;
		this.parametersToDtoConverter = parametersToDtoConverter;
	}

	@GetMapping
	public PageDto<UserDto> getUsers(@RequestParam Map <String, String> parameters) {
		PaginationDto pagination =  parametersToDtoConverter.getPaginationDto(parameters);
		return userService.findAllUsers(pagination);
	}

	@GetMapping("/{id}")
	public UserDto getUserById(@PathVariable long id) {
		return userService.findUserById(id);
	}
}
