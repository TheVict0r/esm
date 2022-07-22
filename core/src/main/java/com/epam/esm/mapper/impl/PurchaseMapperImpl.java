package com.epam.esm.mapper.impl;

import com.epam.esm.dao.entity.Purchase;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class PurchaseMapperImpl implements EntityDtoMapper<Purchase, PurchaseDto> {

	private final ModelMapper mapper;

	@Override
	public Purchase convertToEntity(PurchaseDto dto) {
		log.debug("Converting DTO - {} to Purchase", dto);
		return mapper.map(dto, Purchase.class);
	}

	@Override
	public PurchaseDto convertToDto(Purchase entity) {
		log.debug("Converting Purchase - {} to DTO", entity);
		return mapper.map(entity, PurchaseDto.class);
	}
}
