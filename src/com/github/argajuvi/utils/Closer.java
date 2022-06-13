package com.github.argajuvi.utils;

import java.util.ArrayList;
import java.util.List;

public class Closer implements AutoCloseable {

    private final List<AutoCloseable> closeables = new ArrayList<>();

    public <T extends AutoCloseable> T add(T value) {
        closeables.add(value);
        return value;
    }

    @Override
    public void close() {
        for (AutoCloseable closeable : closeables) {
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
        }
    }

}
