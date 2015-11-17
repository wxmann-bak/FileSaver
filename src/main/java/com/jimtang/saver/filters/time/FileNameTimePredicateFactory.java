package com.jimtang.saver.filters.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

/**
 * Created by tangz on 11/16/2015.
 */
public class FileNameTimePredicateFactory implements TimePredicateFactory<String> {

    public static FileNameTimePredicateFactory withSettings(DateTimeFormatter inputFormatter,
                                                            TimeExtractor fileTimeExtractor) {
        return new FileNameTimePredicateFactory(inputFormatter, fileTimeExtractor);
    }

    private DateTimeFormatter inputFormatter;
    private TimeExtractor fileTimeExtractor;

    FileNameTimePredicateFactory(DateTimeFormatter inputFormatter, TimeExtractor fileTimeExtractor) {
        this.inputFormatter = inputFormatter;
        this.fileTimeExtractor = fileTimeExtractor;
    }

    private class FileNameTimePredicateImpl implements Predicate<String> {

        private final TimePredicate timePredicate;

        private FileNameTimePredicateImpl(TimePredicate timePredicate) {
            this.timePredicate = timePredicate;
        }

        @Override
        public boolean test(String fileName) {
            LocalDateTime timeToTest = fileTimeExtractor.extractDateTime(fileName);
            return timePredicate.test(timeToTest);
        }
    }

    public Predicate<String> after(String time) {
        LocalDateTime boundaryTime = LocalDateTime.parse(time, inputFormatter);
        return new FileNameTimePredicateImpl(new AfterTimePredicate(boundaryTime));
    }

    public Predicate<String> before(String time) {
        LocalDateTime boundaryTime = LocalDateTime.parse(time, inputFormatter);
        return new FileNameTimePredicateImpl(new BeforeTimePredicate(boundaryTime));
    }

    public Predicate<String> between(String time1, String time2) {
        LocalDateTime boundaryTime1 = LocalDateTime.parse(time1, inputFormatter);
        LocalDateTime boundaryTime2 = LocalDateTime.parse(time2, inputFormatter);
        return new FileNameTimePredicateImpl(new BetweenTimePredicate(boundaryTime1, boundaryTime2));
    }
}
