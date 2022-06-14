package com.epam.esm.dto;

import com.epam.esm.service.validation.BasicInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * Basic DTO class for Tag entity
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    @PositiveOrZero(message = "message.validation.tag.id.positive_or_zero", groups = BasicInfo.class)
    private Long id;

    @NotBlank(message = "message.validation.tag.name.not_blank", groups = BasicInfo.class)
    @Size(min = 3, max = 30, message = "message.validation.tag.name.size", groups = BasicInfo.class)
    @Pattern(regexp = "^[a-zA-Z]+[0-9\\s-]*$", message = "message.validation.tag.name.pattern", groups = BasicInfo.class)
    private String name;

    public TagDto(String name) {
        this.name = name;
    }

}