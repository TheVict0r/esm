package com.epam.esm.dto;

import java.io.Serializable;
import java.util.Set;

import com.epam.esm.service.validation.BasicInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends RepresentationModel<UserDto> implements Serializable {
	@PositiveOrZero(message = "message.validation.user.id.positive_or_zero", groups = BasicInfo.class)
	private Long id;
	@NotBlank(message = "message.validation.user.name.not_blank", groups = BasicInfo.class)
	@Size(min = 3, max = 30, message = "message.validation.user.name.size", groups = BasicInfo.class)
	private String name;
	private Set<PurchaseDto> purchases;

}
