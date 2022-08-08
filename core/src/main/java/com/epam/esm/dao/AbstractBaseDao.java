package com.epam.esm.dao;

import java.io.Serializable;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract class for CRUD operations with the datasource.
 *
 * @param <T> entity for CRUD operations.
 */
@Log4j2
public abstract class AbstractBaseDao<T extends Serializable> implements BaseDao<T> {
	private Class<T> classForDao;
	private String className;

	@PersistenceContext
	private EntityManager entityManager;

	public AbstractBaseDao(final Class<T> classToSet) {
		this.classForDao = classToSet;
		this.className = classForDao.getSimpleName();
	}

	/**
	 * Retrieves entity by it's ID.
	 *
	 * @param id
	 *            entity's <b>ID</b>
	 * @return entity
	 */
	@Override
	@Transactional
	public Optional<T> getById(final long id) {
		log.debug("Reading the {} by ID - {}", className, id);
		return Optional.ofNullable(entityManager.find(classForDao, id));
	}

	/**
	 * Creates entity in the datasource
	 *
	 * @param entity
	 *            object containing all data for creating the new entity
	 * @return
	 */
	@Override
	@Transactional
	public T create(T entity) {
		log.debug("Creating the {} - {}", className, entity);
		entityManager.persist(entity);
		return entity;
	}

	/**
	 * Updates entity by its <b>ID</b> in the datasource.
	 *
	 * @param entity
	 *            object containing all data for updating the existing entity
	 * @return
	 */
	@Override
	@Transactional
	public T update(T entity) {
		log.debug("Updating the {} with the new data - {}", className, entity);
		return entityManager.merge(entity);
	}

	/**
	 * Deletes entity by its <b>ID</b> from the datasource.
	 *
	 * @param entity
	 *            entity to be deleted
	 */
	@Override
	@Transactional
	public void delete(T entity) {
		log.debug("Deleting the {} - {}", className, entity);
		entityManager.remove(entity);
	}
}
