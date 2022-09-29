package com.epam.esm.dao.repositories;

import com.epam.esm.dao.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
