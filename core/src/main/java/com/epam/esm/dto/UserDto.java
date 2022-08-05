package com.epam.esm.dto;

import java.io.Serializable;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends RepresentationModel<UserDto> implements Serializable {
    private Long id;
    private String name;
    private Set<PurchaseDto> purchases;

}
