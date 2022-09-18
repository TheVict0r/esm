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
import org.springframework.stereotype.Component;

@Component
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
        //todo think about using null instead of password in return value
        if(passwordEncoder.matches(passwordProvided, userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(nameProvided,
                    passwordProvided, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("The password does not match");
        }
    }

    //todo check this true in return
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
