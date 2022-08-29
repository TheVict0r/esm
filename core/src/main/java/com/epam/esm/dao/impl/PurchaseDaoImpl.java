package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractBaseDao;
import com.epam.esm.dao.PurchaseDao;
import com.epam.esm.dao.entity.Purchase;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class PurchaseDaoImpl extends AbstractBaseDao<Purchase> implements PurchaseDao {
	public static final String FROM_PURCHASE_BY_USER_ID_AND_PURCHASE_ID = "from Purchase p where p.userId = :userId and p.id = :purchaseId";
	@PersistenceContext
	private EntityManager entityManager;

	public PurchaseDaoImpl() {
		super(Purchase.class);
	}

	@Override
	public Optional<Purchase> getPurchaseForUser(Long userId, Long purchaseId) {
		log.info("Reading Purchase with ID - {} for the User with ID - {}", purchaseId, userId);
		return entityManager.createQuery(FROM_PURCHASE_BY_USER_ID_AND_PURCHASE_ID, Purchase.class)
				.setParameter("userId", userId).setParameter("purchaseId", purchaseId).getResultList().stream()
				.findAny();
	}
}
