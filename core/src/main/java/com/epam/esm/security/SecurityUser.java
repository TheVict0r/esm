package com.epam.esm.security;

import com.epam.esm.dto.UserDto;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * {@link org.springframework.security.core.userdetails.UserDetails}
 * implementation used for authentication and authorisation.
 *
 * Wrapper for the {@link com.epam.esm.dto.UserDto} object.
 *
 */
public class SecurityUser implements UserDetails {
	/**
	 * Inner {@link com.epam.esm.dto.UserDto} key object.
	 */
	private final UserDto userDto;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;
	public SecurityUser(UserDto userDto) {
		this.userDto = userDto;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(userDto.getRole());
	}

	@Override
	public String getPassword() {
		return userDto.getPassword();
	}

	@Override
	public String getUsername() {
		return userDto.getName();
	}

	public UserDto getUserDto() {
		return userDto;
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
