package com.jimtang.saver.filters;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Predicate;

/**
 * Created by tangz on 1/24/2016.
 */
public class FilePredicatesTest {

    @Test
    public void testPredicateForFileTypes() throws Exception {
        String[] allowedFileTypes = {"jpg", "png"};
        String allowedFileName = "C:/test/afasdf/abc.jpg";
        String notAllowedFileName = "C:/test/afasdf/def.gif";

        Predicate<String> fileTypeFilter = FilePredicates.forAllowedFileTypes(allowedFileTypes);
        Assert.assertTrue(fileTypeFilter.test(allowedFileName));
        Assert.assertFalse(fileTypeFilter.test(notAllowedFileName));
    }

    @Test
    public void testPredicateForContainingText() throws Exception {
        String containsTextFileName1 = "C:/test/afasdf/abc_def.jpg";
        String containsTextFileName2 = "C:/test/afasdf/def.jpg";
        String notContainsFileName = "C:/test/afasdf/abc_dee.jpg";

        Predicate<String> containingTextFilter = FilePredicates.forContainingText("def");
        Assert.assertTrue(containingTextFilter.test(containsTextFileName1));
        Assert.assertTrue(containingTextFilter.test(containsTextFileName2));
        Assert.assertFalse(containingTextFilter.test(notContainsFileName));
    }
}
