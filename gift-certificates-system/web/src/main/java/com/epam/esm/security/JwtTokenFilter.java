package com.epam.esm.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.ExceptionDetails;
import com.epam.esm.util.MessageKey;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private static final String ENCODING = "UTF-8";
	private final JwtTokenProvider jwtTokenProvider;
	private final MessageSource messageSource;

	@Autowired
	public JwtTokenFilter(JwtTokenProvider provider, MessageSource messageSource) {
		this.jwtTokenProvider = provider;
		this.messageSource = messageSource;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("FILTER");// TODO
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
		try {
			if (token != null && jwtTokenProvider.validateToken(token)) {
				Authentication authentication = jwtTokenProvider.getAuthentication(token);
				if (authentication != null) {
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} /*
				 * else if (!request.getRequestURI().contains("/gift-certificates") ||
				 * (request.getRequestURI().contains("/gift-certificates") &&
				 * !request.getMethod().equals("get"))) { throw new
				 * AccessDeniedException("JWT token is expired or invalid");// TODO записать в
				 * response }
				 */
		} catch (JwtException | IllegalArgumentException e) { // TODO JwtAuthenticationException
			SecurityContextHolder.clearContext();
			String errorMessage = messageSource.getMessage(MessageKey.ACCESS_DENIED, new String[] {},
					request.getLocale());
			response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(ENCODING);
			response.getWriter().write(String.valueOf(new ObjectMapper().writeValueAsString(
					new ExceptionDetails(errorMessage, HttpStatus.FORBIDDEN.value() + ErrorCode.DEFAULT.getCode()))));//TODO status UNAUTHO...
			//TODO throws Exception
			return;
		}
		filterChain.doFilter(request, response);
	}
}
