package com.reactivespring.exception;

import lombok.Data;

@Data
public class ReviewsServerException extends RuntimeException {

    private String message;

    public ReviewsServerException(String message) {
        super(message);
        this.message = message;
    }

}
