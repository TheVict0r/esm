package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {

	// todo add validation annotations

	private Long id;

	private Long userId;

	// + some other annotations
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime date;

	private Integer cost;

	private Set<CertificateDto> certificates;
}
