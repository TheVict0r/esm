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

@Log4j2
@RequiredArgsConstructor
@Component
public class AccessChecker {

    public boolean checkUserId(Authentication authentication, Long userId){
        log.debug("checking user ID - {} and Authentication - {}", userId, authentication);
        CustomUsernamePasswordAuthenticationToken customUsernamePasswordAuthenticationToken = (CustomUsernamePasswordAuthenticationToken) authentication;
        return customUsernamePasswordAuthenticationToken.getUserId().equals(userId);
    }
}
