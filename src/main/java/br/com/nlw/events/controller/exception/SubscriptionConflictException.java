package br.com.nlw.events.controller.exception;

public class SubscriptionConflictException extends RuntimeException{
    public SubscriptionConflictException(String msg){
        super(msg);
    } 
}
