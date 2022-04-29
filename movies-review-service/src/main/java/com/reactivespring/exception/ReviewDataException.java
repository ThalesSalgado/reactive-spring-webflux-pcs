package com.reactivespring.exception;

import lombok.Data;

@Data
public class ReviewDataException extends RuntimeException {

    private String message;

    public ReviewDataException(String s) {
        super(s);
        this.message=s;
    }

}
