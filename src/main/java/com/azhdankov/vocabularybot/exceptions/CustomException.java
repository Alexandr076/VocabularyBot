package com.azhdankov.vocabularybot.exceptions;

public abstract class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}
