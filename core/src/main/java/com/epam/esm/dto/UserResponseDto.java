package com.epam.esm.dto;

import com.epam.esm.security.Role;
import com.epam.esm.service.validation.BasicInfo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

/** Secured DTO class for User entity - no password data */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto extends RepresentationModel<UserResponseDto> implements Serializable {
	@PositiveOrZero(message = "message.validation.user.id.positive_or_zero", groups = BasicInfo.class)
	private Long id;
	@NotBlank(message = "message.validation.user.name.not_blank", groups = BasicInfo.class)
	@Size(min = 3, max = 30, message = "message.validation.user.name.size", groups = BasicInfo.class)
	private String name;
	private Role role;
	private Set<PurchaseDto> purchases = new HashSet<>();

}
