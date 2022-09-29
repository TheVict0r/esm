package com.epam.esm.dao.repositories;

import com.epam.esm.dao.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
