package com.vianavitor.enterprisechat.exceptions;

public class NotFoundResourceException extends RuntimeException {
    public NotFoundResourceException(String message) {
        super(message);
    }
}
