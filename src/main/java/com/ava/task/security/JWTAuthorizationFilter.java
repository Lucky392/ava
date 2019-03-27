package com.ava.task.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ava.task.security.constants.SecurityConstants;
import com.ava.task.service.UserAuditService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ava.task.security.constants.SecurityConstants.HEADER_STRING;
import static com.ava.task.security.constants.SecurityConstants.SECRET;
import static com.ava.task.security.constants.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private static final String EMPTY_STRING = "";

	private UserAuditService userAuditService;

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		if (userAuditService == null) {
			ServletContext servletContext = req.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
			userAuditService = webApplicationContext.getBean(UserAuditService.class);
		}

		final String header = req.getHeader(HEADER_STRING);

		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			chain.doFilter(req, res);
			return;
		}

		final UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(HEADER_STRING);
		if (token != null) {

			final DecodedJWT jwt = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, EMPTY_STRING));

			// parse the token.
			final String user = jwt.getSubject();

			if (user != null) {

				if (jwt.getClaim(SecurityConstants.ADMIN).asBoolean()) {
					userAuditService.createAudit(request.getRequestURI(), request.getMethod(), user, new Date());
				}

				final List<SimpleGrantedAuthority> authorities = jwt.getClaim(SecurityConstants.ROLE)
						.asList(String.class).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

				return new UsernamePasswordAuthenticationToken(user, null, authorities);
			}
		}
		return null;
	}
}