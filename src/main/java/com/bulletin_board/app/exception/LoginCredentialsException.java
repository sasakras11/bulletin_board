package com.bulletin_board.app.exception;

public class LoginCredentialsException extends RuntimeException{
    public LoginCredentialsException(String message){
        super(message);
    }
}
