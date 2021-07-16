package com.epam.esm.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.dto.AuthenticationDto;
import com.epam.esm.dto.SecurityUser;
import com.epam.esm.dto.UserDto;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;

	public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
			UserService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userService = userService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationDto request) {
		System.out.println(request.toString());// TODO
		try {
			String email = request.getEmail();
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));
			System.out.println("1");// TODO
			SecurityUser user = (SecurityUser) authentication.getPrincipal();
			String token = jwtTokenProvider.createToken(email);
			return ResponseEntity.ok(Map.of("email", email, "token", token));// TODO
		} catch (AuthenticationException e) {
			// TODO throw new BadCredentialsException("Invalid username or password");
			throw new RuntimeException();
		}
	}

	@PostMapping("/registration")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto registaration(@RequestBody UserDto userDto) {
		//TODO addLinks
		return userService.createUser(userDto);
	}
}
