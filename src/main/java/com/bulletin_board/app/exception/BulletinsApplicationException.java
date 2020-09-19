package com.bulletin_board.app.exception;

public class BulletinsApplicationException extends RuntimeException{
  private final String pageName;
    public BulletinsApplicationException(String cause,String pageNameToReturn){
        super(cause);
        pageName = pageNameToReturn;
    }


    public String getPageName() {
        return pageName;
    }
}
