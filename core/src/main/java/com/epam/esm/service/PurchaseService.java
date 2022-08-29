package com.epam.esm.service;

import com.epam.esm.dto.PurchaseDto;
import org.springframework.stereotype.Service;

/** Service operations with the {@code Purchase}. */
@Service
public interface PurchaseService {

	/**
	 * Provides Purchase by its ID for User
	 *
	 * @param userId
	 *            User's ID
	 * @param purchaseId
	 *            Purchase's ID
	 * @return requested PurchaseDto
	 */
	PurchaseDto getPurchaseForUser(Long userId, Long purchaseId);

	/**
	 * Adds a new Purchase for the user.
	 *
	 * @param userId
	 *            the ID of the user.
	 * @param purchaseDto
	 *            PurchaseDto need to be added
	 * @return added PurchaseDto
	 */
	PurchaseDto create(Long userId, PurchaseDto purchaseDto);
}
