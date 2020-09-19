package com.bulletin_board.app.service;

import com.bulletin_board.app.exception.BulletinsApplicationException;
import org.springframework.stereotype.Component;

@Component
public class Validator {

  private static final String PASSWORD_PATTERN = "[a-zA-Z0-9]{8,}";
  private static final String EMAIL_PATTERN = "^(.+)@(.+)$";
  private static final String REGISTER_PAGE_NAME = "register";


  public void validateName(String name){
     if(name.length() < 3) throw new BulletinsApplicationException("not valid name",REGISTER_PAGE_NAME); // edit
  }


  public  void validateEmail(String email) {
    if (!email.matches(EMAIL_PATTERN)) {
      throw new BulletinsApplicationException("not valid email",REGISTER_PAGE_NAME);
    }
  }

  public void validatePassword(String pass) {
    if (!pass.matches(PASSWORD_PATTERN)) {
      throw new BulletinsApplicationException("not valid password",REGISTER_PAGE_NAME);
    }
  }


}
