package com.jimtang.saver.plugins;

/**
 * Created by tangz on 10/3/2015.
 */
public class AbstractInputOption<T> implements PluginInputOption {
    T repr;
    public AbstractInputOption(T repr) {
        this.repr = repr;
    }

    @Override
    public T get() {
        return repr;
    }
}
