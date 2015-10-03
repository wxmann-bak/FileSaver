package com.jimtang.saver.util;

/**
 * Created by tangz on 3/30/2015.
 */
public interface FileNameHandler {

    public String buildName(String rawFileName);

    public String buildName(String rawFileName, String fileExtension);
}
