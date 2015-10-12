package com.jimtang.saver.executor.httpresponse;

import com.jimtang.saver.executor.ImageRetrievalException;

/**
 * Created by tangz on 10/11/2015.
 */
public class HTMLTraverseException extends ImageRetrievalException {
    public HTMLTraverseException() {
        super();
    }

    public HTMLTraverseException(String message) {
        super(message);
    }

    public HTMLTraverseException(String message, Throwable cause) {
        super(message, cause);
    }

    public HTMLTraverseException(Throwable cause) {
        super(cause);
    }

    protected HTMLTraverseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
