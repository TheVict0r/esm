package com.epam.esm.config;

import com.epam.esm.security.Role;
import com.epam.esm.security.UserAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	/**
	 * Processes an Authentication request.
	 *
	 * @param http
	 *            allows configuring web based security for specific http requests.
	 * @param userAuthenticationProvider
	 *            Indicates a class can process a specific
	 *            {@link org.springframework.security.core.Authentication}
	 *            implementation.
	 * @return manager for processing an {@link Authentication} request
	 * @throws Exception
	 *             if authentication fails
	 */
	@Bean
	public AuthenticationManager userAuthenticationManagerBean(HttpSecurity http,
			UserAuthenticationProvider userAuthenticationProvider) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		return authenticationManagerBuilder.authenticationProvider(userAuthenticationProvider).build();
	}

	/**
	 * a filter chain which is capable of being matched against an
	 * {@code HttpServletRequest} in order to decide whether it applies to that
	 * request.
	 *
	 * @param http
	 *            allows configuring web based security for specific http requests.
	 * @return a filters for checking http-requests
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic();
		http.csrf().disable();
		http.authorizeRequests().mvcMatchers(HttpMethod.GET, "/certificates/**").permitAll()
				.mvcMatchers("/users/signup", "/locales/**").permitAll()
				.mvcMatchers(HttpMethod.GET, "/users/**", "/certificates/**", "/tags/**")
				.hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority())
				.mvcMatchers(HttpMethod.POST, "/users/{userId}/purchases/")
				.hasAnyAuthority(Role.USER.getAuthority(), Role.ADMIN.getAuthority())
				.mvcMatchers("/users/**", "/certificates/**", "/tags/**", "/generator")
				.hasAuthority(Role.ADMIN.getAuthority()).anyRequest().authenticated();
		return http.build();
	}

	/**
	 * Bean for encoding passwords.
	 *
	 * @return password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
