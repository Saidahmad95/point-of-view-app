package com.example.newpointviewapp.exceptions;

public class MyCustomException extends RuntimeException {

    public MyCustomException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
