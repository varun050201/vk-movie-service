package com.vk.ms.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message){
        super(message);
    }
}
