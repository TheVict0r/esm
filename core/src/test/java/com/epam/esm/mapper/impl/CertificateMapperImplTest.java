package com.epam.esm.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.esm.dao.entity.Certificate;
import com.epam.esm.dto.CertificateDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CertificateMapperImplTest {
	@Autowired
	CertificateMapperImpl certificateMapper;
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	@Test
	void convertToEntityShouldReturnEntity() {
		CertificateDto certificateDto = new CertificateDto().builder().id(5L).name("name 5 test")
				.description("description 5").price(80).duration(80)
				.createDate(LocalDateTime.parse("2022-04-25 13:11:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-25 13:12:03.636", formatter)).build();

		Certificate expectedCertificate = new Certificate().builder().id(5L).name("name 5 test")
				.description("description 5").price(80).duration(80)
				.createDate(LocalDateTime.parse("2022-04-25 13:11:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-25 13:12:03.636", formatter)).build();

		assertEquals(expectedCertificate, certificateMapper.convertToEntity(certificateDto));
	}

	@Test
	void convertToDtoShouldReturnDto() {
		Certificate certificate = new Certificate().builder().id(2L).name("name 2 test").description("description 2")
				.price(20).duration(20).createDate(LocalDateTime.parse("2022-04-22 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-22 23:14:03.636", formatter)).build();

		CertificateDto expectedCertificateDto = new CertificateDto().builder().id(2L).name("name 2 test")
				.description("description 2").price(20).duration(20)
				.createDate(LocalDateTime.parse("2022-04-22 23:14:03.635", formatter))
				.lastUpdateDate(LocalDateTime.parse("2022-04-22 23:14:03.636", formatter)).build();

		assertEquals(expectedCertificateDto, certificateMapper.convertToDto(certificate));
	}

}