package com.rory.demo.app.dtos;

public class ErrorResponseConverterDTO {

    private String message;

    private String reason;

    private int errorCode;

    public ErrorResponseConverterDTO(String message, String reason, int errorCode) {
        this.message = message;
        this.reason = reason;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getReason() {
        return reason;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
