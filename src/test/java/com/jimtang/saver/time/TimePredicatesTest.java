package com.jimtang.saver.time;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Created by tangz on 1/24/2016.
 */
public class TimePredicatesTest {
    LocalDateTime turnOfCentury = LocalDateTime.of(2000, 1, 1, 0, 0);
    LocalDateTime yearBefore = LocalDateTime.of(1999, 1, 1, 0, 0);
    LocalDateTime yearAfter = LocalDateTime.of(2001, 1, 1, 0, 0);
    LocalDateTime tenYearsAfterTurnOfCentury = LocalDateTime.of(2010, 1, 1, 0, 0);

    @Test
    public void testBeforeTimePredicate() throws Exception {
        Predicate<LocalDateTime> filter = TimePredicates.before(turnOfCentury);
        Assert.assertTrue(filter.test(yearBefore));
        Assert.assertFalse(filter.test(yearAfter));
    }

    @Test
    public void testAfterTimePredicate() throws Exception {
        Predicate<LocalDateTime> filter = TimePredicates.after(turnOfCentury);
        Assert.assertFalse(filter.test(yearBefore));
        Assert.assertTrue(filter.test(yearAfter));
    }

    @Test
    public void testBetweenTimePredicate() throws Exception {
        Predicate<LocalDateTime> filter = TimePredicates.between(turnOfCentury, tenYearsAfterTurnOfCentury);
        Assert.assertFalse(filter.test(yearBefore));
        Assert.assertTrue(filter.test(yearAfter));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfBetweenTimeArgumentsNotChronological() throws Exception {
        TimePredicates.between(tenYearsAfterTurnOfCentury, turnOfCentury);
    }
}
