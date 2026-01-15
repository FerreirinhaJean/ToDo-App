package com.github.FerreirinhaJean.ToDo_App.exception;

public class DuplicatedRegisterException extends RuntimeException {
    public DuplicatedRegisterException(String message) {
        super(message);
    }
}
