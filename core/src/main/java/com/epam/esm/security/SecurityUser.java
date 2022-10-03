package com.epam.esm.security;

import com.epam.esm.dto.UserRequestDto;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * {@link org.springframework.security.core.userdetails.UserDetails}
 * implementation used for authentication and authorisation.
 *
 * Wrapper for the {@link UserRequestDto} object.
 *
 */
public class SecurityUser implements UserDetails {
	/**
	 * Inner {@link UserRequestDto} key object.
	 */
	private final UserRequestDto userRequestDto;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;
	public SecurityUser(UserRequestDto userRequestDto) {
		this.userRequestDto = userRequestDto;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(userRequestDto.getRole());
	}

	@Override
	public String getPassword() {
		return userRequestDto.getPassword();
	}

	@Override
	public String getUsername() {
		return userRequestDto.getName();
	}

	public UserRequestDto getUserDto() {
		return userRequestDto;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
