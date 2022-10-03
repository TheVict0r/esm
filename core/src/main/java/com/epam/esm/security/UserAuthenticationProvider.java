package com.epam.esm.security;

import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Indicates a class can process a specific
 * {@link org.springframework.security.core.Authentication} implementation.
 *
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.debug("Authentication started");
		String nameProvided = authentication.getName();
		String passwordProvided = authentication.getCredentials().toString();
		UserDetails userDetails = userService.loadUserByUsername(nameProvided);

		if (passwordEncoder.matches(passwordProvided, userDetails.getPassword())) {
			SecurityUser securityUser = (SecurityUser) userDetails;
			securityUser.getUserDto().setPassword(null);
			return new UsernamePasswordAuthenticationToken(securityUser, null, userDetails.getAuthorities());
		} else {
			throw new BadCredentialsException("The password does not match");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
