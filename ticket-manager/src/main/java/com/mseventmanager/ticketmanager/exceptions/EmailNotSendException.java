package com.mseventmanager.ticketmanager.exceptions;

public class EmailNotSendException extends RuntimeException {
    public EmailNotSendException(String message) {
        super(message);
    }
}