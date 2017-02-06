package com.rory.demo.exceptions;

public class InvoiceAlreadyExistException extends Exception {

    public InvoiceAlreadyExistException(String s) {
        super(s);
    }
}
