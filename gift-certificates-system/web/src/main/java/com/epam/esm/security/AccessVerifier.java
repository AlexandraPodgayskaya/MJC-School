package com.epam.esm.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.epam.esm.dto.JwtUser;

@Component
public class AccessVerifier {

	public void checkAccess(long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
		if (!jwtUser.isAdmin() && jwtUser.getId() != id) {
			throw new AccessDeniedException("access is denied");
		}
	}
}
