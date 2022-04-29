package com.reactivespring.exception;

import lombok.Data;

@Data
public class MoviesInfoServerException extends RuntimeException {

    private String message;

    public MoviesInfoServerException(String message) {
        super(message);
        this.message = message;
    }

}
