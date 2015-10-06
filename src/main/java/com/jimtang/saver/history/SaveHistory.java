package com.jimtang.saver.history;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Created by tangz on 10/5/2015.
 */
public class SaveHistory implements Iterable<File> {

    private static final int DEFAULT_LENGTH = 3;

    private int capacity;
    private Deque<File> history;

    public SaveHistory() {
        this.capacity = DEFAULT_LENGTH;
        this.history = new ArrayDeque<>(DEFAULT_LENGTH);
    }

    public SaveHistory(int capacity) {
        this.capacity = capacity;
        this.history = new ArrayDeque<>(this.capacity);
    }

    public void add(File savedFile) {
        while (history.size() >= capacity) {
            history.removeFirst();
        }
        history.add(savedFile);
    }

    public void removeLatest() {
        history.removeLast();
    }

    public File latest() {
        return history.peekLast();
    }

    @Override
    public Iterator<File> iterator() {
        return history.descendingIterator();
    }
}
