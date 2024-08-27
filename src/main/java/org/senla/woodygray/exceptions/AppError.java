package org.senla.woodygray.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class AppError {
    private int status;
    private String message;
    private Date timestamp;

    public AppError(int status, String messagez) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
