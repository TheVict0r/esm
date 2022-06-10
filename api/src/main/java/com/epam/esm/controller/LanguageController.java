package com.epam.esm.controller;

import com.epam.esm.exception.NonexistentLocaleException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * Provides methods for switching the default value of {@Code java.util.Locale}.
 */
@Log4j2
@RestController
@RequestMapping(value = "/languages")
public class LanguageController {

    public static final String EN = "en";
    public static final String RU = "ru";

    /**
     * Switch default locale with the accordance to the request.
     *
     * @param language string representation of the language to be switched to
     * @return a confirmation message about the result of switching attempt
     */
    @GetMapping(path = "/{language}")
    public String switchLanguage(@PathVariable("language") String language) {
        log.info("Switching locale to {}", language);
        switch (language) {
            case "en":
                Locale.setDefault(Locale.ENGLISH);
                return EN;
            case "ru":
                Locale.setDefault(new Locale(RU));
                return RU;
            default:
                Locale.setDefault(Locale.ENGLISH);
                throw new NonexistentLocaleException(language);
        }
    }

}