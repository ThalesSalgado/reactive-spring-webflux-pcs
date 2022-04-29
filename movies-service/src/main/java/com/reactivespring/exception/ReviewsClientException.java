package com.reactivespring.exception;

import lombok.Data;

@Data
public class ReviewsClientException extends RuntimeException {

    private String message;

    public ReviewsClientException(String message) {
        super(message);
        this.message = message;
    }

}
