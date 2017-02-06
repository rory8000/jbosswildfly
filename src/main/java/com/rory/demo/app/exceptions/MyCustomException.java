package com.rory.demo.app.exceptions;

import javax.ws.rs.core.Response;

public class MyCustomException extends RuntimeException {

    private String reason;
    private Response.Status status;
    private int errorCode;

    public MyCustomException(String message, String reason, Response.Status status, int errorCode) {
        super(message);
        this.reason = reason;
        this.status = status;
        this.errorCode = errorCode;
    }

    public String getReason() {
        return reason;
    }

    public Response.Status getStatus() {
        return status;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
