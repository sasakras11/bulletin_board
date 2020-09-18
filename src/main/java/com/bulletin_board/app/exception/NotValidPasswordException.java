package com.bulletin_board.app.exception;

public class NotValidPasswordException extends RuntimeException {

  public NotValidPasswordException(String message) {
    super(message);
  }
}
