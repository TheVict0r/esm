package com.epam.esm.service;

import com.epam.esm.dto.PurchaseDto;
import org.springframework.stereotype.Service;

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
}
