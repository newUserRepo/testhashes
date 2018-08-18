package com.example.demo.util;

public interface ShowData {

    static <T> void println(final T t) {
        System.out.println(t);
    }

    static <T> void print(final T t) {
        System.out.print(t);
    }
}
