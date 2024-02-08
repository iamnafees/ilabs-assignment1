package com.ilabs.CartService.exception;
public class NotValidQuantityException extends RuntimeException {

    private String message;

    public NotValidQuantityException(String message){
        super(message);
    }

}
