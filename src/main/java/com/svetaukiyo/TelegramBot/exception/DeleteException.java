package com.svetaukiyo.TelegramBot.exception;

public class DeleteException extends RuntimeException {

    public DeleteException(String errorMessage) {
        super(errorMessage);
    }
}
