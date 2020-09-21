package com.bulletin_board.app.service;

import com.bulletin_board.app.exception.BulletinsApplicationException;
import org.springframework.stereotype.Component;

@Component
public class Validator {

  private static final String PASSWORD_PATTERN = "[a-zA-Z0-9]{8,}";
  private static final String EMAIL_PATTERN = "^(.+)@(.+)$";
  private static final String REGISTER_PAGE_NAME = "register";


  public void validateName(String name){
     if(name.length() < 3) throw new BulletinsApplicationException("not valid name. Should be more then 2 characters long",REGISTER_PAGE_NAME); // edit
  }


  public  void validateEmail(String email) {
    if (!email.matches(EMAIL_PATTERN)) {
      throw new BulletinsApplicationException("not valid email",REGISTER_PAGE_NAME);
    }
  }

  public void validatePassword(String pass) {
    if (!pass.matches(PASSWORD_PATTERN)) {
      throw new BulletinsApplicationException("not valid password(should be more then 8 characters long and only latin symbols and numbers)",REGISTER_PAGE_NAME);
    }
  }

  public void validateBulletinHeader(String header){
    if(header.length()<8 || header.length()> 30) {
      throw new BulletinsApplicationException("header length should be from 8 to 30 characters long","add_bulletin");
    }
  }

  public void validateBulletinText(String text){
    if(text.length()<20 || text.length()> 300) {
      throw new BulletinsApplicationException("text length should be from 20 to 300 characters long","add_bulletin");
    }
  }
}
