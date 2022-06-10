package com.epam.esm.mapper.impl;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.mapper.EntityDtoMapper;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CertificateMapperImpl implements EntityDtoMapper<Certificate, CertificateDto> {

    private ModelMapper mapper;

    @Autowired
    public CertificateMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Certificate convertToEntity(@NonNull CertificateDto dto) {
        log.debug("Converting DTO - {} to Certificate", dto);
        return mapper.map(dto, Certificate.class);
    }

    @Override
    public CertificateDto convertToDto(@NonNull Certificate entity) {
        log.debug("Converting Certificate - {} to DTO", entity);
        return mapper.map(entity, CertificateDto.class);
    }

}