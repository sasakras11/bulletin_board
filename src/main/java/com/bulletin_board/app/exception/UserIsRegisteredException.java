package com.bulletin_board.app.exception;

public class UserIsRegisteredException extends RuntimeException{

    public UserIsRegisteredException(String message){
        super(message);
    }
}
