package com.epam.esm.exception;

import java.text.MessageFormat;

public abstract class AbstractLocalizedCustomException extends RuntimeException {

    private String messageKey;
    private Object[] params;

    public AbstractLocalizedCustomException(String messageKey, Object[] params) {
        this.messageKey = messageKey;
        this.params = params;
    }

    @Override
    public String getLocalizedMessage() {
        String pattern = MessageLocaleHandler.getLocalisedMessage(messageKey);
        return MessageFormat.format(pattern, params);
    }


}