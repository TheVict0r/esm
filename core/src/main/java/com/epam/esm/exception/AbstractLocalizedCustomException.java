package com.epam.esm.exception;

import lombok.Data;

@Data
public abstract class AbstractLocalizedCustomException extends RuntimeException {

    private String messageKey;
    private Object[] params;

    public AbstractLocalizedCustomException(String messageKey, Object[] params) {
        this.messageKey = messageKey;
        this.params = params;
    }

}