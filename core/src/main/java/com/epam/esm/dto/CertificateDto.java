package com.epam.esm.dto;

import com.epam.esm.service.validation.AdvancedInfo;
import com.epam.esm.service.validation.BasicInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Basic DTO class for Certificate entity */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDto implements Serializable {

  @PositiveOrZero(
      message = "message.validation.certificate.id.positive_or_zero",
      groups = BasicInfo.class)
  private Long id;

  @NotBlank(message = "message.validation.certificate.name.not_blank", groups = BasicInfo.class)
  @Size(
      min = 3,
      max = 30,
      message = "message.validation.certificate.name.size",
      groups = BasicInfo.class)
  @Pattern(
      regexp = "^[a-zA-Z0-9\\s-]*$",
      message = "message.validation.certificate.name.pattern",
      groups = BasicInfo.class)
  private String name;

  @NotBlank(
      message = "message.validation.certificate.description.not_blank",
      groups = BasicInfo.class)
  @Size(
      min = 5,
      max = 300,
      message = "message.validation.certificate.description.size",
      groups = BasicInfo.class)
  @Pattern(
      regexp = "^[a-zA-Z0-9\\s.,:;!?&@-]*$",
      message = "message.validation.certificate.description.pattern",
      groups = BasicInfo.class)
  private String description;

  @NotNull(message = "message.validation.certificate.price.not_null", groups = BasicInfo.class)
  @PositiveOrZero(
      message = "message.validation.certificate.price.positive_or_zero",
      groups = BasicInfo.class)
  private Integer price;

  @NotNull(message = "message.validation.certificate.duration.not_null", groups = BasicInfo.class)
  @PositiveOrZero(
      message = "message.validation.certificate.duration.positive_or_zero",
      groups = BasicInfo.class)
  private Integer duration;

  @NotNull(
      message = "message.validation.certificate.create_date.not_null",
      groups = AdvancedInfo.class)
  @PastOrPresent(
      message = "message.validation.certificate.create_date.past_or_present",
      groups = AdvancedInfo.class)
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  // @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private LocalDateTime createDate;

  @NotNull(
      message = "message.validation.certificate.last_update_date.not_null",
      groups = AdvancedInfo.class)
  @PastOrPresent(
      message = "message.validation.certificate.last_update_date.past_or_present",
      groups = AdvancedInfo.class)
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  // @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
  private LocalDateTime lastUpdateDate;

  private Set<@Valid TagDto> tags;
}
