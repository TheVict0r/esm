package com.epam.esm.service;

import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserNoPasswordDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

/** Service operations with the {@code User}. */
public interface UserService extends UserDetailsService {

	/**
	 * Provides all existing {@code UserDto} objects.
	 *
	 * @param page
	 *            page number (used for pagination)
	 * @param size
	 *            amount of {@code Users} per page
	 * @return the list containing all {@code UserDto} objects converted from all
	 *         {@code Users} existing in the datasource
	 */
	List<UserNoPasswordDto> getAll(int page, int size);

	/**
	 * Finds the User by its <b>ID</b> in the datasource.
	 *
	 * @param id
	 *            User's <b>ID</b>
	 * @return <b>UserDto</b>, returned by the corresponding {@code DAO level}
	 *         method
	 */
	UserNoPasswordDto getById(Long id);

	/**
	 * Creates a new User in the datasource.
	 *
	 * @param userDto
	 *            DTO of User with data for entity need to be created
	 * @return DTO object of created User entity
	 */

	UserNoPasswordDto create(UserDto userDto);

	/**
	 * Provides all purchases for User by its ID
	 *
	 * @param userId
	 *            User ID
	 * @return list containing all purchases for User
	 */
	Set<PurchaseDto> getAllPurchasesByUserId(Long userId);

	/**
	 * Checks if user exists in the datasource.
	 *
	 * @param userId
	 *            The ID of user need to be checked.
	 * @return {@code true} if user with provided ID exists in the datasource,
	 *         {@code false} otherwise.
	 */
	boolean isUserExist(Long userId);
}
