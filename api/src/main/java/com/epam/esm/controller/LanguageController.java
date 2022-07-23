package com.epam.esm.controller;

import com.epam.esm.exception.NonExistentLocaleException;
import java.util.Locale;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides methods for switching the default value of {@code java.util.Locale}.
 */
@Log4j2
@RestController
@RequestMapping(value = "/locales")
public class LanguageController {

	/**
	 * Switch default locale with the accordance to the request.
	 *
	 * @param defaultLocale
	 *            string representation of the default locale to be switched to
	 * @return a confirmation message about the result of switching attempt
	 */
	@GetMapping(path = "/{locale}")
	public Language switchLanguage(@PathVariable("locale") String defaultLocale) {
		log.info("Switching locale to {}", defaultLocale);
		Language language = null;
		try {
			language = Language.valueOf(defaultLocale.toUpperCase());
		} catch (IllegalArgumentException e) {
			Locale.setDefault(Locale.ENGLISH);
			throw new NonExistentLocaleException(defaultLocale);
		}
		Locale.setDefault(new Locale(language.toString()));
		return language;
	}

	enum Language {
		EN, RU
	}
}
