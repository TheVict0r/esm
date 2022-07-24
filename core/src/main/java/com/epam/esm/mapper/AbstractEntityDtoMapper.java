package com.epam.esm.mapper;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

/**
 * An abstract class for implementing the mapping of an {@code <E>} entity to
 * its {@code <D>} DTO and vice versa.
 *
 * The entity and its DTO must have the same field names for a proper mapping.
 */
@Log4j2
@AllArgsConstructor
public abstract class AbstractEntityDtoMapper<E, D> {
	private final Class<E> entityClass;
	private final Class<D> dtoClass;
	private final ModelMapper mapper;

	/**
	 * Converts DTO to entity.
	 *
	 * @param dto
	 *            DTO
	 * @return entity of DTO provided
	 */
	public E convertToEntity(@NonNull D dto) {
		log.debug("Converting DTO - {} to {}", dto, entityClass.getSimpleName());
		return mapper.map(dto, entityClass);
	}

	/**
	 * Converts entity to DTO.
	 *
	 * @param entity
	 *            entity
	 * @return DTO of entity provided
	 */
	public D convertToDto(@NonNull E entity) {
		log.debug("Converting  - {} to {}", entity, dtoClass.getSimpleName());
		return mapper.map(entity, dtoClass);
	}
}
