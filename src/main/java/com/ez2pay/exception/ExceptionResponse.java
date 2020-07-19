package com.ez2pay.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Schema(title = "Exception Response", description = "Exception response used for all kind of exceptions")
public class ExceptionResponse implements Serializable {

    private final Date timeStamp;
    private final String message;
    private final String detail;
}
