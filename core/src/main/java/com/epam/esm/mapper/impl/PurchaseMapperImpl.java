package com.epam.esm.mapper.impl;

import com.epam.esm.dao.entity.Purchase;
import com.epam.esm.dto.PurchaseDto;
import com.epam.esm.mapper.AbstractEntityDtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PurchaseMapperImpl extends AbstractEntityDtoMapper<Purchase, PurchaseDto> {
	public PurchaseMapperImpl(ModelMapper mapper) {
		super(Purchase.class, PurchaseDto.class, mapper);
	}
}
