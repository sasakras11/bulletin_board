package com.bulletin_board.app.service;

import com.bulletin_board.app.exception.NotValidEmailException;
import com.bulletin_board.app.exception.NotValidPasswordException;
import org.springframework.stereotype.Component;

@Component
public class Validator {

  private static final String PASSWORD_PATTERN = "[a-zA-Z0-9]{8,}";
  private static final String EMAIL_PATTERN = "^(.+)@(.+)$";

  public void validate(String email, String password) {
    validateEmail(email);
    validatePassword(password);
  }

  private void validateEmail(String str) {
    if (!str.matches(EMAIL_PATTERN)) {
      throw new NotValidEmailException("registration");
    }
  }

  private void validatePassword(String str) {
    if (!str.matches(PASSWORD_PATTERN)) {
      throw new NotValidPasswordException("registration");
    }
  }
}
