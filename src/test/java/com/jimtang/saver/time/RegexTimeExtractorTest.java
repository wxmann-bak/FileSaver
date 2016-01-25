package com.jimtang.saver.time;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.core.Is.is;

/**
 * Created by tangz on 1/25/2016.
 */
public class RegexTimeExtractorTest {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmm");
    private RegexTimeExtractor extractor = new RegexTimeExtractor() {
        @Override
        protected String getRegexExp() {
            return "\\d{8}_\\d{4}";
        }

        @Override
        public DateTimeFormatter getFormatter() {
            return formatter;
        }
    };

    String canExtractTime = "IMG_20160125_1200.jpg";
    String cannotExtractTime = "IMG_20160125.jpg";

    @Test
    public void shouldTestCanOrCannotExtractTime() throws Exception {
        Assert.assertTrue(extractor.canExtractTime(canExtractTime));
        Assert.assertFalse(extractor.canExtractTime(cannotExtractTime));
    }

    @Test
    public void shouldExtractTimeIfCanExtract() {
        Assert.assertThat(extractor.extractDateTime(canExtractTime), is(LocalDateTime.of(2016, 1, 25, 12, 0)));
    }

    @Test
    public void shouldReturnNullIfCannotExtractTime() {
        Assert.assertNull(extractor.extractDateTime(cannotExtractTime));
    }
}
