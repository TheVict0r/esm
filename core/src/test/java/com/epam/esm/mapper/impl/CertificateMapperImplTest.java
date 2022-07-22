package com.epam.esm.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.esm.TestConfig;
import com.epam.esm.TestEntityProvider;
import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dto.CertificateDto;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
class CertificateMapperImplTest {

	@Autowired
	CertificateMapperImpl certificateMapper;

	@Autowired
	TestEntityProvider entityProvider;

	@BeforeEach
	void setUp() {
		Locale.setDefault(Locale.ENGLISH);
	}

	@Test
	void convertToEntityShouldReturnEntity() {
		CertificateDto certificateDto = entityProvider.getCertificate5dto();
		Certificate expectedCertificate = entityProvider.getCertificate5();
		assertEquals(expectedCertificate, certificateMapper.convertToEntity(certificateDto));
	}

	@Test
	void convertToDtoShouldReturnDto() {
		Certificate certificate = entityProvider.getCertificate2();
		CertificateDto expectedCertificateDto = entityProvider.getCertificate2dto();
		assertEquals(expectedCertificateDto, certificateMapper.convertToDto(certificate));
	}
}
