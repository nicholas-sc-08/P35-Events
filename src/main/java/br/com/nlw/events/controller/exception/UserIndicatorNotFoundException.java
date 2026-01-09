package br.com.nlw.events.controller.exception;

public class UserIndicatorNotFoundException extends RuntimeException {
    public UserIndicatorNotFoundException(String msg) {
        super(msg);
    }
}
