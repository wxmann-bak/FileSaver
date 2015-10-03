package com.jimtang.saver.util;

/**
 * Created by tangz on 3/30/2015.
 */
public class FileNameException extends RuntimeException {
    public FileNameException() {
        super();
    }

    public FileNameException(String message) {
        super(message);
    }

    public FileNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNameException(Throwable cause) {
        super(cause);
    }

    protected FileNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
