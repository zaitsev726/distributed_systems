package ru.nsu.zebra.client;

import ru.nsu.zebra.client.migration.impl.TimestampStorageJsonImpl;

import java.io.IOException;
import java.time.Instant;

public class Main {
    public static void main(String[] args) throws IOException {
        var a = new TimestampStorageJsonImpl();
        System.out.println(a.getTimestamp("1"));
        a.updateTimestamp("1", Instant.now());

    }
}