package com.BookMySlot.exception;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String message ){
        super(message);

    }
    public DuplicateResourceException(String field , String value){
        super(field + "already Exists: " + value);
    }
}
