package com.epam.esm.exception;

/** Custom exception in the case when new object was not created due to internal server problems. */
public class ResourceNotCreatedException extends AbstractLocalizedCustomException {

  public static final String MESSAGE_KEY = "message.resource_not_created";

  public ResourceNotCreatedException(Object resource) {
    super(MESSAGE_KEY, new Object[] {resource});
  }
}
