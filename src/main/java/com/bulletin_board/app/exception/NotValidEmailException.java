package com.bulletin_board.app.exception;

public class NotValidEmailException extends RuntimeException {

  public NotValidEmailException(String message) {
    super(message);
  }
}
