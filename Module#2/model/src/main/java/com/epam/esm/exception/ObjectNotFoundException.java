package com.epam.esm.exception;

public class ObjectNotFoundException extends Exception{
    public static final long serialVersionUID = -7990117502328917902L;

    public ObjectNotFoundException() {
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
