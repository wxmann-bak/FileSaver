package com.jimtang.saver.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by tangz on 3/30/2015.
 */
public class TimestampAppender implements FileNameHandler {

    private DateFormat dateFormat;

    public TimestampAppender(String dateFormatStr, String timeZoneStr) {
        dateFormat = new SimpleDateFormat(dateFormatStr);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZoneStr));
    }

    private String getTimeStamp() {
        return dateFormat.format(new Date());
    }

    @Override
    public String buildName(String rawFileName) {
        String[] fileParts = rawFileName.split("\\.");
        if (fileParts.length != 2) {
            throw new FileNameException("File name: " + rawFileName + " is not in the expected format: <raw util name>.<util extension>");
        }
        return String.format("%s-%s.%s", fileParts[0], getTimeStamp(), fileParts[1]);
    }

    @Override
    public String buildName(String rawFileName, String fileExtension) {
        String[] fileParts = rawFileName.split("\\.");
        return String.format("%s-%s.%s", fileParts[0], getTimeStamp(), fileExtension);
    }
}
