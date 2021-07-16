package com.epam.esm.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${jwt.expiration}")
	private Long expirationInMinutes;
	@Value("${jwt.secret}")
	private String jwtSecret;
	@Value("${jwt.header}")
	private String authorizationHeader;// TODO нужно ли?

	private UserDetailsService userDetailsService;

	@Autowired
	public JwtTokenProvider(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@PostConstruct
	protected void init() {
		jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
	}

	public String createToken(String email) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime validity = now.plusMinutes(expirationInMinutes);
		return Jwts.builder().setSubject(email).setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
				.setExpiration(Date.from(validity.atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
	}

	public boolean validateToken(String token) {
		if (token == null) {
			return false;
		}
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return claimsJws.getBody().getExpiration().after(new Date());
		} catch (ExpiredJwtException ex) {
			throw new RuntimeException();
			// throw new TokenExpiredException(MessageKey.TOKEN_EXPIRED);//TODO
			// JwtAuthorisationException
		} catch (RuntimeException e) {
			throw new RuntimeException();
			// throw new InvalidTokenException(MessageKey.INVALID_TOKEN);
		}
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
		System.out.println("GET AUTHENTICATION" + userDetails.toString());// TODO
		return new UsernamePasswordAuthenticationToken(userDetails, StringUtils.EMPTY, userDetails.getAuthorities());
	}

	// TODO
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader(HttpHeaders.AUTHORIZATION);
	}
}
