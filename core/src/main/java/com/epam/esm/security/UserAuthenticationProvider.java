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
        SecurityUser securityUser = (SecurityUser)userDetails;

        if (passwordEncoder.matches(passwordProvided, userDetails.getPassword())) {
            return new CustomUsernamePasswordAuthenticationToken(nameProvided,
                    null, userDetails.getAuthorities(), securityUser.getUserDto().getId());
        } else {
            throw new BadCredentialsException("The password does not match");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
