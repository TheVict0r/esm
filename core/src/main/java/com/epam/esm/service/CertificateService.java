package com.epam.esm.service;

import com.epam.esm.dto.CertificateDto;
import java.util.List;
import org.springframework.stereotype.Service;

/** Service operations with the {@code Certificate}. */
@Service
public interface CertificateService extends BasicService<CertificateDto> {

  /**
   * Searches {@code Certificates} with tags (all params are optional and can be used in
   * conjunction).
   *
   * @param tagName {@code Tag's} name
   * @param name {@code Certificate's} name
   * @param description {@code Certificate's} description
   * @param sort sort by some {@code Certificate's} parameter
   * @return The list with <b>DTOs</b> of found {@code Certificates}
   */
  List<CertificateDto> search(String tagName, String name, String description, String sort);
}
