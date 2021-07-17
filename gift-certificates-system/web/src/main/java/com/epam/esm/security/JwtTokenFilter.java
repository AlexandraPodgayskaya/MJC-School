package com.epam.esm.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Autowired
	public JwtTokenFilter(JwtTokenProvider provider) {
		this.jwtTokenProvider = provider;
	}

	/*
	 * @Override public void doFilter(ServletRequest request, ServletResponse
	 * response, FilterChain filterChain) throws IOException, ServletException {
	 * System.out.println("FILTER");//TODO String token =
	 * jwtTokenProvider.resolveToken((HttpServletRequest) request); try { if (token
	 * != null && jwtTokenProvider.validateToken(token)) { Authentication
	 * authentication = jwtTokenProvider.getAuthentication(token); if
	 * (authentication != null) {
	 * SecurityContextHolder.getContext().setAuthentication(authentication); } } }
	 * catch (Exception e) { // TODO JwtAuthenticationException
	 * SecurityContextHolder.clearContext(); // ((HttpServletResponse)
	 * response).sendError(e.getHttpStatus().value()); // throw new
	 * JwtAuthenticationException("JWT token is expired or invalid"); }
	 * filterChain.doFilter(request, response); }
	 */

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
			}
		} catch (Exception e) { // TODO JwtAuthenticationException
			SecurityContextHolder.clearContext();
			// ((HttpServletResponse) response).sendError(e.getHttpStatus().value());
			// throw new JwtAuthenticationException("JWT token is expired or invalid");
		}
		filterChain.doFilter(request, response);

	}

}
