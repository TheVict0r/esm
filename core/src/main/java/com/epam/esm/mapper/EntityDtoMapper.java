package com.epam.esm.mapper;

import org.springframework.stereotype.Component;

/** Mapping entity <E> and its DTO <D> */
@Component
public interface EntityDtoMapper<E, D> {

  /**
   * Converts DTO to entity.
   *
   * @param dto DTO
   * @return entity of DTO provided
   */
  E convertToEntity(D dto);

  /**
   * Converts entity to DTO.
   *
   * @param entity entity
   * @return DTO of entity provided
   */
  D convertToDto(E entity);
}
