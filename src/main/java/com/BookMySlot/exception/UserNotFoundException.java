package com.BookMySlot.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
       super(message);
    }
    public UserNotFoundException(Long userId){
        super("User Not Found with this id : " + userId);

    }

}
