package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto extends RepresentationModel<PurchaseDto> implements Serializable {

	// todo add validation annotations

	private Long id;

	private Long userId;

	// + some other annotations
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime date;

	private Integer cost;

	private Set<CertificateDto> certificates;

	public PurchaseDto(Long userId, Set<CertificateDto> certificates) {
		this.userId = userId;
		this.certificates = certificates;
	}

}
