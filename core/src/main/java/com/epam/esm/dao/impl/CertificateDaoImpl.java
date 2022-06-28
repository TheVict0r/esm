package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.search.SearchProvider;
import com.epam.esm.exception.ResourceNotCreatedException;
import com.epam.esm.exception.ResourceNotFoundException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class CertificateDaoImpl implements CertificateDao {

  public static final String READ_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id = ?";
  public static final String CREATE_NEW_CERTIFICATE =
      "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) VALUES (?, ?, ?, ?, ?, ?)";
  public static final String DELETE_CERTIFICATE_BY_ID = "DELETE FROM gift_certificate WHERE id = ?";
  public static final String UPDATE_CERTIFICATE_BY_ID =
      "UPDATE gift_certificate SET name = ?, description = ?, price = ?, duration = ?, last_update_date = ? WHERE id = ?";
  public static final String READ_CERTIFICATES_BY_TAG_ID =
      "SELECT * FROM gift_certificate JOIN gift_certificate_tag ON gift_certificate.id = gift_certificate_tag.tag_id WHERE tag_id = ?";

  private final JdbcTemplate jdbcTemplate;
  private final KeyHolder keyHolder;
  private final SearchProvider searchProvider;

  @PersistenceContext private EntityManager entityManager;

  @Autowired
  public CertificateDaoImpl(
      JdbcTemplate jdbcTemplate, KeyHolder keyHolder, SearchProvider searchProvider) {
    this.jdbcTemplate = jdbcTemplate;
    this.keyHolder = keyHolder;
    this.searchProvider = searchProvider;
  }

  @Override
  public Optional<Certificate> readById(long id) {
    log.debug("Reading Certificate by ID - {}", id);
    return Optional.ofNullable(entityManager.find(Certificate.class, id));
  }

  //
  //    Session session = sessionFactory.openSession();
  //    session.beginTransaction();
  //    Certificate certificate = session.get(Certificate.class, id);
  //    certificate.getTags().stream().findFirst();
  //    session.getTransaction().commit();
  //    session.close();
  //    return Optional.ofNullable(certificate);
  //  }

  @Override
  public List<Certificate> search(String tagName, String name, String description, String sort) {
    log.debug(
        "Searching Certificate. Tag name - {}, Certificate name - {}, Certificate description - {}, sort - {}",
        tagName,
        name,
        description,
        sort);
    String query = searchProvider.provideQuery(tagName, name, description, sort);
    String[] args = searchProvider.provideArgs(tagName, name, description);
    return jdbcTemplate.query(query, args, new BeanPropertyRowMapper<>(Certificate.class));
  }

  @Override
  public Certificate create(Certificate certificate) {
    log.debug("Creating Certificate - {}", certificate);
    int rowsAffected =
        jdbcTemplate.update(
            connection -> {
              PreparedStatement ps =
                  connection.prepareStatement(
                      CREATE_NEW_CERTIFICATE, Statement.RETURN_GENERATED_KEYS);
              ps.setString(1, certificate.getName());
              ps.setString(2, certificate.getDescription());
              ps.setInt(3, certificate.getPrice());
              ps.setInt(4, certificate.getDuration());
              ps.setObject(5, certificate.getCreateDate());
              ps.setObject(6, certificate.getLastUpdateDate());
              return ps;
            },
            keyHolder);
    if (rowsAffected != 1) {
      log.error("Certificate '{}' was not created.", certificate);
      throw new ResourceNotCreatedException(certificate);
    }
    certificate.setId(keyHolder.getKey().longValue());
    return certificate;
  }

  @Override
  public Certificate update(Certificate certificate) {
    log.debug("Replacing Certificate, the new Certificate data - {}", certificate);
    long id = certificate.getId();
    int rowsAffected =
        jdbcTemplate.update(
            UPDATE_CERTIFICATE_BY_ID,
            certificate.getName(),
            certificate.getDescription(),
            certificate.getPrice(),
            certificate.getDuration(),
            certificate.getLastUpdateDate(),
            id);
    if (rowsAffected != 1) {
      throw new ResourceNotFoundException(id);
    }
    return certificate;
  }

  @Override
  public long deleteById(long id) {
    log.debug("Deleting Certificate with ID - {}", id);
    int rowsAffected = jdbcTemplate.update(DELETE_CERTIFICATE_BY_ID, id);
    if (rowsAffected != 1) {
      throw new ResourceNotFoundException(id);
    }
    return id;
  }

  @Override
  public List<Certificate> retrieveCertificatesByTagId(long tagId) {
    log.debug("Retrieving the List of Certificates by Tag's ID - {}", tagId);
    return jdbcTemplate.query(
        READ_CERTIFICATES_BY_TAG_ID,
        new Object[] {tagId},
        new int[] {Types.VARCHAR},
        new BeanPropertyRowMapper<>(Certificate.class));
  }
}
