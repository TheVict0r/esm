package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.exception.ResourceNotCreatedException;
import com.epam.esm.exception.ResourceNotFoundException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class TagDaoImpl implements TagDao {

  public static final String CREATE_NEW_TAG = "INSERT INTO tag (name) VALUES (?)";
  public static final String READ_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?";
  public static final String READ_TAG_BY_NAME = "SELECT * FROM tag WHERE name = ?";
  public static final String READ_ALL_TAGS = "SELECT * FROM tag";
  public static final String UPDATE_TAG_BY_ID = "UPDATE tag SET name = ? WHERE id = ?";
  public static final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id = ?";
  public static final String READ_TAGS_BY_CERTIFICATE_ID =
      "SELECT * FROM tag JOIN gift_certificate_tag ON tag.id = gift_certificate_tag.tag_id WHERE gift_certeficate_id = ?";
  private static final String SAVE_TAG_TO_CERTIFICATE =
      "INSERT INTO gift_certificate_tag (gift_certeficate_id, tag_id) VALUES (?, ?)";
  private static final String DELETE_TAGS_FROM_CERTIFICATE =
      "DELETE FROM gift_certificate_tag WHERE gift_certeficate_id = ?";

  public static final int ILLEGAL_TAG_ID = -1;

  private final JdbcTemplate jdbcTemplate;
  private final KeyHolder keyHolder;

  @Autowired
  public TagDaoImpl(JdbcTemplate jdbcTemplate, KeyHolder keyHolder) {
    this.jdbcTemplate = jdbcTemplate;
    this.keyHolder = keyHolder;
  }

  @Override
  public Optional<Tag> readById(long id) {
    log.debug("Reading the Tag by ID - {}.", id);
    return jdbcTemplate
        .query(
            READ_TAG_BY_ID,
            new Object[] {id},
            new int[] {Types.VARCHAR},
            new BeanPropertyRowMapper<>(Tag.class))
        .stream()
        .findAny();
  }

  @Override
  public List<Tag> searchAll() {
    log.debug("Reading all Tags.");
    return jdbcTemplate.query(READ_ALL_TAGS, new BeanPropertyRowMapper<>(Tag.class));
  }

  @Override
  public Tag create(Tag tag) {
    log.debug("Creating the Tag - {}.", tag);
    int rowsAffected =
        jdbcTemplate.update(
            connection -> {
              PreparedStatement ps =
                  connection.prepareStatement(CREATE_NEW_TAG, Statement.RETURN_GENERATED_KEYS);
              ps.setString(1, tag.getName());
              return ps;
            },
            keyHolder);
    if (rowsAffected != 1) {
      log.error("Tag '{}' was not created.", tag);
      throw new ResourceNotCreatedException(tag);
    }
    tag.setId(keyHolder.getKey().longValue());
    return tag;
  }

  @Override
  public Tag update(Tag tag) {
    log.debug("Updating the Tag - {}.", tag);
    long id = tag.getId();
    int rowsAffected = jdbcTemplate.update(UPDATE_TAG_BY_ID, tag.getName(), id);
    if (rowsAffected != 1) {
      throw new ResourceNotFoundException(id);
    }
    return tag;
  }

  @Override
  public long deleteById(long id) {
    log.debug("Deleting the Tag by ID - {}.", id);
    int rowsAffected = jdbcTemplate.update(DELETE_TAG_BY_ID, id);
    if (rowsAffected != 1) {
      throw new ResourceNotFoundException(id);
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
