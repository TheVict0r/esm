package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;

import java.sql.Types;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.epam.esm.exception.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Log4j2
public class TagDaoImpl implements TagDao {

    public static final int ILLEGAL_TAG_ID = -1;

    private final CertificateDao certificateDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public TagDaoImpl(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
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
        entityManager.merge(tagUpdate);
        return tagUpdate;
    }

    @Override
    @Transactional
    public long delete(Tag tag) {
        log.debug("Deleting the Tag - {}.", tag);
        entityManager.remove(tag);
        return tag.getId();
    }

    @Override
    public Set<Tag> retrieveTagsByCertificateId(long certificateId) {
        log.debug("Retrieving the set of tags by Certificate ID - {}.", certificateId);
        Certificate certificate = certificateDao.readById(certificateId).orElseThrow(() -> {
            log.error("There is no tag with ID '{}' in the database", certificateId);
            return new ResourceNotFoundException(certificateId);
        });
        return certificate.getTags();
//    List<Tag> tagList =
//        jdbcTemplate.query(
//            READ_TAGS_BY_CERTIFICATE_ID,
//            new Object[] {certificateId},
//            new int[] {Types.VARCHAR},
//            new BeanPropertyRowMapper<>(Tag.class));
//    return new HashSet<>(tagList);
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


    private Optional<Tag> readByName(String tagName) {
        log.debug("Reading the Tag name - {}.", tagName);
        return entityManager.createQuery("from Tag where name = :tagname")
                .setParameter("tagname", tagName)
                .getResultList().stream()
                .findAny();
    }

}
