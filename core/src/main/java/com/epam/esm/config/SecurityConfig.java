package com.epam.esm.config;

import com.epam.esm.dao.entity.Role;
import com.epam.esm.security.UserAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public AuthenticationManager userAuthenticationManagerBean(HttpSecurity http, UserAuthenticationProvider userAuthenticationProvider) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.authenticationProvider(userAuthenticationProvider).build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic();
        //http.formLogin();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/certificates/**").permitAll()
                .mvcMatchers("/users/signup", "/locales/**").permitAll()
                .mvcMatchers(HttpMethod.GET, "/users/**", "/certificates/**", "/tags/**").hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority())
                .mvcMatchers(HttpMethod.POST, "/users/{userId}/purchases/").hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority())
                .mvcMatchers("/users/**", "/certificates/**", "/tags/**", "/generator/**").hasAuthority(Role.ADMIN.getAuthority())

               .anyRequest().authenticated();
//                .anyRequest().permitAll();
        http.csrf().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
