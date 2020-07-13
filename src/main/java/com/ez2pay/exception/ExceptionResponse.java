package com.ez2pay.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;

@Schema(title = "Exception Response", description = "Exception response used for all kind of exceptions")
public class ExceptionResponse implements Serializable {

    private final Date timeStamp;
    private final String message;
    private final String detail;

    public ExceptionResponse(Date timeStamp, String message, String detail) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.detail = detail;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }
}
