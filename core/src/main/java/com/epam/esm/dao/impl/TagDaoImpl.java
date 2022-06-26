package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.exception.DuplicateEntryException;
import com.epam.esm.exception.ResourceNotFoundException;
import java.sql.Types;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class TagDaoImpl implements TagDao {

  public static final String READ_TAG_BY_NAME = "SELECT * FROM tag WHERE name = ?";
  public static final String READ_TAGS_BY_CERTIFICATE_ID =
      "SELECT * FROM tag JOIN gift_certificate_tag ON tag.id = gift_certificate_tag.tag_id WHERE gift_certificate_id = ?";
  private static final String SAVE_TAG_TO_CERTIFICATE =
      "INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (?, ?)";
  private static final String DELETE_TAGS_FROM_CERTIFICATE =
      "DELETE FROM gift_certificate_tag WHERE gift_certificate_id = ?";

  public static final int ILLEGAL_TAG_ID = -1;

  private final JdbcTemplate jdbcTemplate;

  private final SessionFactory sessionFactory;

  @Autowired
  public TagDaoImpl(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
    this.jdbcTemplate = jdbcTemplate;
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Optional<Tag> readById(long id) {
    log.debug("Reading the Tag by ID - {}.", id);
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Tag tag = session.get(Tag.class, id);
    session.getTransaction().commit();
    session.close();
    return Optional.ofNullable(tag);
  }

  @Override
  public List<Tag> searchAll() {
    log.debug("Reading all Tags.");
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
    CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
    Root<Tag> root = criteriaQuery.from(Tag.class);
    criteriaQuery.select(root);
    List<Tag> allTags = session.createQuery(criteriaQuery).getResultList();
    //    /*===============HQL=======================*/
    //    List<Tag> allTags = session.createQuery("from Tag").getResultList();
    //
    session.getTransaction().commit();
    session.close();
    return allTags;
  }

  @Override
  public Tag create(Tag tag) {
    log.debug("Creating the Tag - {}.", tag);
    Session session = sessionFactory.openSession();
    try {
      session.beginTransaction();
      session.save(tag);
      session.getTransaction().commit();
    } catch (ConstraintViolationException e) {
      if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
          || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
        session.getTransaction().rollback();
      }
      throw new DuplicateEntryException(e.getSQLException().getMessage());
    } finally {
      session.close();
    }
    return tag;
  }

  @Override
  public Tag update(Tag tagUpdate) {
    log.debug("Updating the Tag - {}.", tagUpdate);
    Session session = sessionFactory.openSession();
    Tag tagInDatasource;
    try {
      session.beginTransaction();
      tagInDatasource = session.get(Tag.class, tagUpdate.getId());

      if (tagInDatasource == null) {
        log.error("There is no tag with ID '{}' in the database", tagUpdate.getId());
        throw new ResourceNotFoundException(tagUpdate.getId());
      }

      if (!(tagUpdate.getName().equals(tagInDatasource.getName()))) {
        tagInDatasource.setName(tagUpdate.getName());
        session.getTransaction().commit();
      }
    } catch (ConstraintViolationException e) {
      if (session.getTransaction().getStatus() == TransactionStatus.ACTIVE
          || session.getTransaction().getStatus() == TransactionStatus.MARKED_ROLLBACK) {
        session.getTransaction().rollback();
      }
      throw new DuplicateEntryException(e.getSQLException().getMessage());
    } finally {
      session.close();
    }

    return tagInDatasource;
  }

  @Override
  public long deleteById(long id) {
    log.debug("Deleting the Tag by ID - {}.", id);
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Tag tag = session.get(Tag.class, id);

    if (tag == null) {
      log.error("There is no tag with ID '{}' in the database", id);
      throw new ResourceNotFoundException(id);
    } else {
      session.delete(tag);
    }
    session.getTransaction().commit();
    session.close();
    return id;
  }

  @Override
  public Set<Tag> retrieveTagsByCertificateId(long certificateId) {
    log.debug("Retrieving the set of tags by Certificate ID - {}.", certificateId);
    List<Tag> tagList =
        jdbcTemplate.query(
            READ_TAGS_BY_CERTIFICATE_ID,
            new Object[] {certificateId},
            new int[] {Types.VARCHAR},
            new BeanPropertyRowMapper<>(Tag.class));
    return new HashSet<>(tagList);
  }

  @Override
  public boolean isTagExists(Tag tag) {
    log.debug("Checking is Tag - {} exists.", tag);
    return readByName(tag.getName()).isPresent();
  }

  @Override
  public long findIdByTag(Tag tag) {
    log.debug("Searching Tag - {} by it's name.", tag);
    Optional<Tag> tagRetrievedByName = readByName(tag.getName());
    long tagID = ILLEGAL_TAG_ID;
    if (tagRetrievedByName.isPresent()) {
      tagID = tagRetrievedByName.get().getId();
    }
    return tagID;
  }

  @Override
  public void saveTagToCertificate(long certificateId, long tagId) {
    log.debug("Saving Tag with ID - {} to Certificate with ID - {}.", tagId, certificateId);
    jdbcTemplate.update(SAVE_TAG_TO_CERTIFICATE, certificateId, tagId);
  }

  @Override
  public void deleteAllTagsFromCertificate(long certificateId) {
    log.debug("Deleting all tags from certificate with ID - {}.", certificateId);
    jdbcTemplate.update(DELETE_TAGS_FROM_CERTIFICATE, certificateId);
  }

  private Optional<Tag> readByName(String tagName) {
    log.debug("Reading the Tag name - {}.", tagName);
    return jdbcTemplate
        .query(
            READ_TAG_BY_NAME,
            new Object[] {tagName},
            new int[] {Types.VARCHAR},
            new BeanPropertyRowMapper<>(Tag.class))
        .stream()
        .findAny();
  }
}
