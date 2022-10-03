package com.epam.esm.dao.repositories;

import com.epam.esm.dao.entity.Purchase;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** Data access operations with the {@code Purchase} entity. */
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	String PURCHASE_BY_USER_ID_AND_PURCHASE_ID = "from Purchase p where p.userId = :userId and p.id = :purchaseId";

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
	@Query(PURCHASE_BY_USER_ID_AND_PURCHASE_ID)
	Optional<Purchase> getPurchaseForUser(Long userId, Long purchaseId);
}
