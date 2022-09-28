package com.epam.esm.security;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.entity.User;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * Helper class for checking access rights during the authorisation process.
 *
 */
@Log4j2
@RequiredArgsConstructor
@Component
public class AccessChecker {

    /**
     * Checks if provided {@link org.springframework.security.core.Authentication} object contains the same user ID as provided param userId.
     *
     * @param authentication Authentication object
     * @param userId user's ID
     * @return true if {@link org.springframework.security.core.Authentication} object contains the same user ID as provided param userId
     */
    public boolean checkUserId(Authentication authentication, Long userId){
        log.debug("checking user ID - {} and Authentication - {}", userId, authentication);
        SecurityUser securityUser = (SecurityUser)authentication.getPrincipal();
        return securityUser.getUserDto().getId().equals(userId);
    }
}
