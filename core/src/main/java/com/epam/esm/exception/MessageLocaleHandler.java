package com.epam.esm.exception;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Helper class to get localized text message.
 */
public class MessageLocaleHandler {

    public static final String BASE_NAME = "messages";

    /**
     * Provides localized text message by the key.
     *
     * @param messageKey key for retrieving text message
     * @return localized text message
     */
    public static String getLocalisedMessage(String messageKey) {
        return ResourceBundle.getBundle(BASE_NAME, Locale.getDefault()).getString(messageKey);
    }

}