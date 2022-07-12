package com.epam.esm.dao;

import java.util.Optional;

/**
 * Basic interface for CRUD operations with datasource.
 *
 * @param <E> entity for CRUD operations
 */
public interface BasicDao<E> {

  /**
   * Creates a new entity in the datasource.
   *
   * @param entity object containing all data for creating the new entity
   * @return the new entity
   */
  E create(E entity);

  /**
   * Reads entity by its <b>ID</b> from the datasource.
   *
   * @param id entity's <b>ID</b>
   * @return Optional container object which may or may not contain entity
   */
  Optional<E> readById(long id);

  /**
   * Updates entity by its <b>ID</b> in the datasource.
   *
   * @param entity object containing all data for updating the existing entity
   * @return the updated entity
   */
  E update(E entity);

  /**
   * Deletes entity by its <b>ID</b> from the datasource.
   *
   * @param entity entity to be deleted
   */
  void delete(E entity);
}
