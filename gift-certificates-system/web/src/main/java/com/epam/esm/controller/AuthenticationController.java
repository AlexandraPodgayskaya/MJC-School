package com.epam.esm.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.AuthenticationDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private static final String EMAIL = "email";
	private static final String TOKEN = "token";
	private static final String AUTHENTICATE = "authenticate";

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;

	@Autowired
	public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
			UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
	}

	@PostMapping("/login")
	public Map<String, String> authenticate(@RequestBody AuthenticationDto request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		String token = jwtTokenProvider.createToken(request.getEmail());
		return Map.of(EMAIL, request.getEmail(), TOKEN, token);
	}

	@PostMapping("/registration")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto registaration(@RequestBody UserDto userDto) {
		UserDto newUser = userService.createUser(userDto);
		addLinks(newUser);
		return newUser;
	}

	private void addLinks(UserDto userDto) {
		userDto.add(linkTo(methodOn(AuthenticationController.class)
				.authenticate(new AuthenticationDto(userDto.getEmail(), userDto.getPassword()))).withRel(AUTHENTICATE));
	}
}
