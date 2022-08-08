package com.epam.esm.dto;

import com.epam.esm.service.validation.AdvancedInfo;
import com.epam.esm.service.validation.BasicInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto extends RepresentationModel<PurchaseDto> implements Serializable {
	@PositiveOrZero(message = "message.validation.purchase.id.positive_or_zero", groups = BasicInfo.class)
	private Long id;

	@NotNull(message = "message.validation.purchase.user_id.not_null", groups = BasicInfo.class)
	@PositiveOrZero(message = "message.validation.user.id.positive_or_zero", groups = BasicInfo.class)
	private Long userId;

	@NotNull(message = "message.validation.purchase.create_date.not_null", groups = AdvancedInfo.class)
	@PastOrPresent(message = "message.validation.purchase.create_date.past_or_present", groups = AdvancedInfo.class)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime date;

	@NotNull(message = "message.validation.purchase.cost.not_null", groups = AdvancedInfo.class)
	@PositiveOrZero(message = "message.validation.purchase.cost.positive_or_zero", groups = AdvancedInfo.class)
	private Integer cost;

	private Set<CertificateDto> certificates;

	public PurchaseDto(Long userId, Set<CertificateDto> certificates) {
		this.userId = userId;
		this.certificates = certificates;
	}

}
