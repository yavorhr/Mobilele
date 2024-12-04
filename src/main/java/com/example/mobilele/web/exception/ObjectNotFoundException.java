package com.example.mobilele.web.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {

  public ObjectNotFoundException(String message) {
    super(message);
  }
}
