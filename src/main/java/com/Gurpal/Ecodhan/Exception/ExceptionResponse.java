package com.Gurpal.Ecodhan.Exception;

public class ExceptionResponse {
    String error;
    public ExceptionResponse(String error)
    {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
