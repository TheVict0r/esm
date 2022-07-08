package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.search.SearchProvider;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Log4j2
public class CertificateDaoImpl implements CertificateDao {

  private final SearchProvider searchProvider;

  @PersistenceContext private EntityManager entityManager;

  @Autowired
  public CertificateDaoImpl(SearchProvider searchProvider) {
    this.searchProvider = searchProvider;
  }

  @Override
  @Transactional
  public Optional<Certificate> readById(long id) {
    log.debug("Reading Certificate by ID - {}", id);
    return Optional.ofNullable(entityManager.find(Certificate.class, id));
  }

  @Override
  public List<Certificate> search(String tagName, String name, String description, String sort) {
    log.debug(
        "Searching Certificate. Tag name - {}, Certificate name - {}, Certificate description - {}, sort - {}",
        tagName,
        name,
        description,
        sort);


    // show ALL certificates
   // return entityManager.createQuery("Select c from Certificate c join c.tags t ").getResultList();


    return entityManager.createQuery("SELECT DISTINCT c FROM Certificate c JOIN c.tags t ORDER BY c.createDate DESC").getResultList();
 //   return entityManager.createQuery("SELECT DISTINCT c FROM Certificate c JOIN FETCH c.tags t ORDER BY c.createDate DESC").getResultList();



//    //search by Certificate name
//    TypedQuery<Certificate> query = entityManager.createQuery("Select c from Certificate c join c.tags t where c.name=:nameProvided", Certificate.class);
//    return query.setParameter("nameProvided", name).getResultList();


 //   //search by Certificate description
//    TypedQuery<Certificate> query = entityManager.createQuery("Select c from Certificate c join c.tags t where c.description=:descriptionProvided", Certificate.class);
//    return query.setParameter("descriptionProvided", description).getResultList();

//    //search by TagName
//    TypedQuery<Certificate> query = entityManager.createQuery("Select c from Certificate c join c.tags t where t.name=:tagNameProvided", Certificate.class);
//    return query.setParameter("tagNameProvided", tagName).getResultList();



    // old version with JDBC template
    //    String query = searchProvider.provideQuery(tagName, name, description, sort);
    //    String[] args = searchProvider.provideArgs(tagName, name, description);
    // return jdbcTemplate.query(query, args, new BeanPropertyRowMapper<>(Certificate.class));

  }

  @Override
  @Transactional
  public Certificate create(Certificate certificate) {
    log.debug("Creating Certificate - {}", certificate);
    entityManager.persist(certificate);
    return certificate;
  }

  @Override
  @Transactional
  public Certificate update(Certificate certificateUpdate) {
    log.debug("Replacing Certificate, the new Certificate data - {}", certificateUpdate);
    entityManager.merge(certificateUpdate);
    return certificateUpdate;
  }

  @Override
  public long delete(Certificate certificate) {
    log.debug("Deleting Certificate - {}", certificate);
    entityManager.remove(certificate);
    return certificate.getId();
  }

  @Override
  public List<Certificate> retrieveCertificatesByTagId(long tagId) {
    log.debug("Retrieving the List of Certificates by Tag's ID - {}", tagId);
    return entityManager.createNativeQuery("SELECT * FROM gift_certificate JOIN gift_certificate_tag ON gift_certificate.id = gift_certificate_tag.gift_certificate_id WHERE gift_certificate_tag.tag_id = :id", Certificate.class)
            .setParameter("id", tagId).getResultList();
  }

}
