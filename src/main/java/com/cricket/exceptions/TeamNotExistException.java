package com.cricket.exceptions;

public class TeamNotExistException extends Exception{
    public TeamNotExistException(String message) {
        super(message);
    }
}
