package com.epam.esm.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.SecurityUser;
import com.epam.esm.entity.User;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

	private final UserDao userDao;

	@Autowired
	public JwtUserDetailsService(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("GO TO LOAD-USER");// TODO
		Optional<User> foundUser = userDao.findByEmail(email);
		System.out.println("LOAD-USER" + foundUser.get().toString());// TODO
		if (foundUser.isEmpty()) {
			throw new UsernameNotFoundException("user doesn't exists");
		}
		User user = foundUser.get();
		return new SecurityUser(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRole());
	}

}
