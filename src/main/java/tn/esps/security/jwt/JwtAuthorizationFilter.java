package tn.esps.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import tn.esps.security.WebSecurityConstant;
import tn.esps.security.service.UserDetailsServiceImpl;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		String token = null;
		String username = null;
		
		if (authorizationHeader != null && authorizationHeader.startsWith(WebSecurityConstant.TOKEN_PREFIX)) {
			token = authorizationHeader.substring(7, authorizationHeader.length());

			try {
				username = jwtTokenProvider.getSubject(token);
			} catch (Exception ex) {
				logger.error("Unable to get JWT token -> Message: {}", ex);
			}
			
			
		} else {
			logger.error(WebSecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
		}
		

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

			if (jwtTokenProvider.isTokenValid(userDetails, token)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			
		}
		
		
		filterChain.doFilter(request, response);
	}

}
