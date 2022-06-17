package com.epam.esm.controller;

import com.epam.esm.exception.NonexistentLocaleException;
import java.util.Locale;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Provides methods for switching the default value of {@Code java.util.Locale}. */
@Log4j2
@RestController
@RequestMapping(value = "/locales")
public class LanguageController {

  public static final String EN = "en";
  public static final String RU = "ru";

  /**
   * Switch default locale with the accordance to the request.
   *
   * @param defaultLocale string representation of the default locale to be switched to
   * @return a confirmation message about the result of switching attempt
   */
  @GetMapping(path = "/{locale}")
  public String switchLanguage(@PathVariable("locale") String defaultLocale) {
    log.info("Switching locale to {}", defaultLocale);
    switch (defaultLocale) {
      case "en":
        Locale.setDefault(Locale.ENGLISH);
        return EN;
      case "ru":
        Locale.setDefault(new Locale(RU));
        return RU;
      default:
        Locale.setDefault(Locale.ENGLISH);
        throw new NonexistentLocaleException(defaultLocale);
    }
  }
}
