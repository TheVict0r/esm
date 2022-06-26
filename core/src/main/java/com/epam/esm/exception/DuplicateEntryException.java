package com.epam.esm.exception;

public class DuplicateEntryException extends AbstractLocalizedCustomException {

  public static final String MESSAGE_KEY = "message.duplicate_key";

  public DuplicateEntryException(String originalMessage) {
    super(MESSAGE_KEY, new Object[] {originalMessage});
  }
}
