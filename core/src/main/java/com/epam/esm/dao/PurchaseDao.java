package com.epam.esm.dao;

import com.epam.esm.dao.entity.Purchase;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/** Data access operations with the {@code Purchase}. */
@Repository
public interface PurchaseDao extends BaseDao<Purchase> {
	/**
	 * Provides Purchase by its ID for User
	 *
	 * @param userId
	 *            User's ID
	 * @param purchaseId
	 *            Purchase's ID
	 * @return An Optional value containing the requested Purchase instance if it
	 *         exists, or a null value otherwise.
	 */
	Optional<Purchase> getPurchaseForUser(Long userId, Long purchaseId);
}
