package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import java.sql.Types;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

  @PersistenceContext private EntityManager entityManager;

  @Autowired
  public TagDaoImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Tag> readById(long id) {
    log.debug("Reading the Tag by ID - {}.", id);
    return Optional.ofNullable(entityManager.find(Tag.class, id));
  }

  @Override
  public List<Tag> searchAll() {
    log.debug("Reading all Tags.");
    return entityManager.createQuery("from Tag").getResultList();
  }

  @Override
  @Transactional
  public Tag create(Tag tag) {
    log.debug("Creating the Tag - {}.", tag);
    entityManager.persist(tag);
    return tag;
  }

  @Override
  @Transactional
  public Tag update(Tag tagUpdate) {
    log.debug("Updating the Tag - {}.", tagUpdate);
    long id = tagUpdate.getId();
    Tag tag = entityManager.find(Tag.class, id);
    if (tag == null) {
      log.error("There is no tag with ID '{}' in the database", id);
      throw new ResourceNotFoundException(id);
    } else {
      entityManager.merge(tagUpdate);
    }
    return tagUpdate;
  }

  @Override
  @Transactional
  public long deleteById(long id) {
    log.debug("Deleting the Tag by ID - {}.", id);
    Tag tag = entityManager.find(Tag.class, id);
    if (tag == null) {
      log.error("There is no tag with ID '{}' in the database", id);
      throw new ResourceNotFoundException(id);
    } else {
      entityManager.remove(tag);
    }
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
