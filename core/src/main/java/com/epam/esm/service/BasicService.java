package com.epam.esm.service;

/**
 * Basic interface for CRUD service operations with DTO.
 *
 * @param <E>
 *            DTO for CRUD operations
 */
public interface BasicService<E> {

	/**
	 * Creates a new entity in the datasource.
	 *
	 * @param dto
	 *            DTO object with data for entity need to be created
	 * @return <b>DTO</b> of created entity
	 */
	E create(E dto);

	/**
	 * Finds the entity by its <b>ID</b> in the datasource.
	 *
	 * @param id
	 *            entity's <b>ID</b>
	 * @return <b>DTO</b> of entity, returned by the corresponding {@code DAO level}
	 *         method
	 */
	E findById(Long id);

	/**
	 * Replaces the particular entity's data, the entity selected by provided
	 * <b>ID</b>.
	 *
	 * @param id
	 *            entity's <b>ID</b>
	 * @param dto
	 *            <b>DTO</b> with data
	 * @return @return <b>DTO</b> of replaced entity
	 */
	E updateById(Long id, E dto);

	/**
	 * Deletes entity by its <b>ID</b> from the datasource.
	 *
	 * @param id
	 *            entity's <b>ID</b>
	 * @return <b>ID</b> of deleted entity
	 */
	long deleteById(Long id);
}
