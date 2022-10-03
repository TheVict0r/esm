package com.epam.esm.security;

import org.springframework.security.core.GrantedAuthority;

/** Enum foe defining roles for users. Can be used as GrantedAuthority. */

public enum Role implements GrantedAuthority {

	USER, ADMIN;

	@Override
	public String getAuthority() {
		return name();
	}
}