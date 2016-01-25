package com.jimtang.saver.history;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;

/**
 * Created by tangz on 1/25/2016.
 */
public class SaveHistoryTest {

    int count;

    @Before
    public void setUp() throws Exception {
        count = 0;
    }

    private File mockFile() {
        File file = Mockito.mock(File.class);
        Mockito.when(file.getAbsolutePath()).thenReturn(count + ".jpg");
        return file;
    }

    @Test
    public void testShouldBeEmpty() throws Exception {
        SaveHistory history = new SaveHistory();
        Assert.assertTrue(history.isEmpty());

        history.add(mockFile());
        Assert.assertFalse(history.isEmpty());
    }

    @Test
    public void testShouldGetLatest() {
        SaveHistory history = new SaveHistory();
        while (count < 5) {
            history.add(mockFile());
            Assert.assertThat(history.latest().getAbsolutePath(), is(count + ".jpg"));
            count++;
        }
    }

    @Test
    public void testShouldGetLatestWithNonDefaultCapacity() {
        SaveHistory history = new SaveHistory(2);
        while (count < 5) {
            history.add(mockFile());
            Assert.assertThat(history.latest().getAbsolutePath(), is(count + ".jpg"));
            count++;
        }
    }

    @Test
    public void testShouldCorrectlyAddAndIterateThroughHistory() {
        SaveHistory history = new SaveHistory(4);
        while (count < 5) {
            history.add(mockFile());
            count++;
        }
        int i = 4;
        for (Iterator<File> itr = history.iterator(); itr.hasNext(); ) {
            Assert.assertThat(itr.next().getAbsolutePath(), is(i + ".jpg"));
            i--;
        }
        // This is to make sure we only have 4 items in the SaveHistory
        Assert.assertThat(i+1, is(0));
    }
}
