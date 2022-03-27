package com.cricket.exceptions;

public class PlayerAlreadyExistException extends Exception{

    public PlayerAlreadyExistException(String message) {
        super(message);
    }
}
