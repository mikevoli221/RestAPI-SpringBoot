package com.ez2pay.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Schema(title = "Exception Response", description = "Exception response used for all kind of exceptions")
public class ExceptionResponse implements Serializable {
    private final Timestamp timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
}
