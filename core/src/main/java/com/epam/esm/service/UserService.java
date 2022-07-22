package com.epam.esm.service;

import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.dto.UserDto;
import java.util.List;

public interface UserService {

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
	List<UserDto> searchAll(int page, int size);

	/**
	 * Finds the User by its <b>ID</b> in the datasource.
	 *
	 * @param id
	 *            User's <b>ID</b>
	 * @return <b>UserDto</b>, returned by the corresponding {@code DAO level}
	 *         method
	 */
	UserDto findById(Long id);

	/**
	 * Provides all purchases for User by its ID
	 *
	 * @param userId
	 *            User ID
	 * @return list containing all purchases for User
	 */
	List<PurchaseDto> findAllPurchasesByUserId(Long userId);
}
