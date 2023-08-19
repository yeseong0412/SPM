package com.example.spm.Exception;

public class OutOfStockException extends Throwable {
    public OutOfStockException(String massage) throws RuntimeException{
        super(massage);
    }
}
