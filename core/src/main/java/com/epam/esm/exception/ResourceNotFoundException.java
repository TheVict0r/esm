package com.epam.esm.exception;

/** Custom exception in the case if there is no entity requested by client in the datasource. */
public class ResourceNotFoundException extends AbstractLocalizedCustomException {

  public static final String MESSAGE_KEY = "message.resource_not_found";

  public ResourceNotFoundException(long id) {
    super(MESSAGE_KEY, new Object[] {id});
  }
}
