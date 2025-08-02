package com.vianavitor.enterprisechat.exceptions;

// used when the database table does not accept duplicate registers
public class DuplicateRegisterException extends RuntimeException {
    public DuplicateRegisterException(String message) {
        super(message);
    }
}
