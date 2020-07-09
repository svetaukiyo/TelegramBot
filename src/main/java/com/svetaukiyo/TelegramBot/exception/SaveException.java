package com.svetaukiyo.TelegramBot.exception;

public class SaveException extends RuntimeException {

    public SaveException(String errorMessage) {
        super(errorMessage);
    }
}
