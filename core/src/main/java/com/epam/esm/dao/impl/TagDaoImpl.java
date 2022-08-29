package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractBaseDao;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dao.entity.Tag;
import com.epam.esm.dao.provider.PaginationProvider;
import com.epam.esm.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class TagDaoImpl extends AbstractBaseDao<Tag> implements TagDao {

	public static final int ILLEGAL_TAG_ID = -1;
	public static final String FROM_TAG = "from Tag";
	public static final String READ_TAG_BY_NAME = "from Tag where name = :tagName";
	private static final String GET_MOST_USED_TAG = """
			SELECT t.id, t.name FROM tag AS t
			JOIN gift_certificate_tag AS gct ON t.id = gct.tag_id
			JOIN gift_certificate AS gc ON gct.gift_certificate_id = gc.id
			JOIN purchase_gift_certificate AS pgc ON gc.id = pgc.gift_certificate_id
			JOIN purchase AS p ON pgc.purchase_id = p.id
			WHERE p.user_id =
			(SELECT  user_id FROM purchase
			GROUP BY user_id ORDER BY SUM(cost) DESC LIMIT 1 )
			GROUP BY t.name HAVING count(t.id) =
			(SELECT count(gct.tag_id) AS tag_count
			FROM gift_certificate_tag AS gct
			JOIN gift_certificate AS gc ON gct.gift_certificate_id = gc.id
			JOIN purchase_gift_certificate AS pgc ON gc.id = pgc.gift_certificate_id
			JOIN purchase AS p ON pgc.purchase_id = p.id
			WHERE p.user_id =
			(SELECT  user_id AS richest_user_id FROM purchase
			GROUP BY user_id ORDER BY SUM(cost) DESC LIMIT 1)
			GROUP BY gct.tag_id ORDER BY tag_count DESC LIMIT 1)
			        """;
	private final CertificateDao certificateDao;

	@PersistenceContext
	private EntityManager entityManager;

	private final PaginationProvider paginationProvider;

	@Autowired
	public TagDaoImpl(CertificateDao certificateDao, PaginationProvider paginationProvider) {
		super(Tag.class);
		this.certificateDao = certificateDao;
		this.paginationProvider = paginationProvider;
	}

	@Override
	public List<Tag> getAll(int page, int size) {
		log.debug("Reading all Tags. Page № - {}, size - {}", page, size);
		TypedQuery<Tag> query = entityManager.createQuery(FROM_TAG, Tag.class);
		paginationProvider.providePagination(query, page, size);
		return query.getResultList();
	}

	@Override
	public Set<Tag> getTagsByCertificateId(long certificateId) {
		log.debug("Retrieving the set of tags by Certificate ID - {}.", certificateId);
		Certificate certificate = certificateDao.getById(certificateId).orElseThrow(() -> {
			log.error("There is no tag with ID '{}' in the database", certificateId);
			return new ResourceNotFoundException(certificateId);
		});
		return certificate.getTags();
	}

	@Override
	public boolean isExist(Tag tag) {
		log.debug("Checking is Tag - {} exists.", tag);
		return readByName(tag.getName()).isPresent();
	}

	@Override
	public long getId(Tag tag) {
		log.debug("Searching Tag - {} by it's name.", tag);
		Optional<Tag> tagRetrievedByName = readByName(tag.getName());
		long tagID = ILLEGAL_TAG_ID;
		if (tagRetrievedByName.isPresent()) {
			tagID = tagRetrievedByName.get().getId();
		}
		return tagID;
	}

	@Override
	public List<Tag> getMostUsedTag() {
		log.debug("Getting the most widely used tag of a user with the highest cost of all orders.");
		Query query = entityManager.createNativeQuery(GET_MOST_USED_TAG, Tag.class);
		return query.getResultList();
	}

	private Optional<Tag> readByName(String tagName) {
		log.debug("Reading Tag by name - {}.", tagName);
		return entityManager.createQuery(READ_TAG_BY_NAME, Tag.class).setParameter("tagName", tagName).getResultList()
				.stream().findAny();
	}
}
