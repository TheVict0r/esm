package com.epam.esm.mapper.impl;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.mapper.AbstractEntityDtoMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/** Mapper for Certificate <-> CertificateDto operations */
@Component
public class CertificateMapperImpl extends AbstractEntityDtoMapper<Certificate, CertificateDto> {
	public CertificateMapperImpl(ModelMapper mapper) {
		super(Certificate.class, CertificateDto.class, mapper);
	}
}
