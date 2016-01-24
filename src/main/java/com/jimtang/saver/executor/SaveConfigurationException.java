package com.jimtang.saver.executor;

/**
 * Created by tangz on 1/24/2016.
 */
public class SaveConfigurationException extends Exception {

    public SaveConfigurationException() {
    }

    public SaveConfigurationException(String message) {
        super(message);
    }

    public SaveConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveConfigurationException(Throwable cause) {
        super(cause);
    }

    public SaveConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
