package com.epam.esm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<UserDto> getUsers() {
		return userService.findAllUsers();
	}

	@GetMapping("/{id}")
	public UserDto getUserById(@PathVariable long id) {
		return userService.findUserById(id);
	}
}
