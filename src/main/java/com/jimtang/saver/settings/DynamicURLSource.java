package com.jimtang.saver.settings;

import com.jimtang.saver.executor.SaveExecutor;

import java.net.URI;

/**
 * Created by tangz on 2/20/2016.
 */
public interface DynamicURLSource extends SaveExecutor {
    String getHost();
    URI getURI();
}
