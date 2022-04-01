package br.com.bootcamp.meetup.exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String s){
        super(s);
    }
}
