package com.instagram.numble_instagram.exception;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class CustomException extends RuntimeException {
    public final Map<String, String> validation = new ConcurrentHashMap<>();

    public CustomException(String message) {
        super(message);
    }

    public abstract int getStatusCode();

    public void addValidation(String filedName, String message) {
        validation.put(filedName, message);
    }
}
