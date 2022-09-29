package com.epam.esm.dto;

import com.epam.esm.dao.entity.Role;
import com.epam.esm.service.validation.BasicInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/** Secured DTO class for User entity - no password data */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserNoPasswordDto extends RepresentationModel<UserNoPasswordDto> implements Serializable {
	@PositiveOrZero(message = "message.validation.user.id.positive_or_zero", groups = BasicInfo.class)
	private Long id;
	@NotBlank(message = "message.validation.user.name.not_blank", groups = BasicInfo.class)
	@Size(min = 3, max = 30, message = "message.validation.user.name.size", groups = BasicInfo.class)
	private String name;
	private Role role;
	private Set<PurchaseDto> purchases = new HashSet<>();

}
