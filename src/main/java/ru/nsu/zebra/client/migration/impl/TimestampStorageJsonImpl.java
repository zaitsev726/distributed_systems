package ru.nsu.zebra.client.migration.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.nsu.zebra.client.migration.TimestampStorage;

import java.io.*;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashMap;
import java.util.Optional;

public class TimestampStorageJsonImpl implements TimestampStorage {
    private final File filePath = new File("./timestamp-storage.json");
    private final ObjectMapper mapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);;

    private final HashMap<String, String> timestampMap;

    public TimestampStorageJsonImpl() throws IOException {
        if (filePath.exists()) {
            this.timestampMap = mapper.readValue(filePath, HashMap.class);
        } else {
            filePath.createNewFile();
            timestampMap = new HashMap<>();
            flushDataToFile(mapper.writeValueAsString(timestampMap));
        }
    }

    @Override
    public synchronized Optional<Instant> getTimestamp(String databaseId) {
        var timestamp = timestampMap.get(databaseId);
        if (timestamp == null)
            return Optional.empty();
        return Optional.of(Instant.parse(timestamp));
    }

    @Override
    public synchronized void updateTimestamp(String databaseId, Instant updatedTime) {
        timestampMap.put(databaseId, updatedTime.toString());
        try {
            flushDataToFile(mapper.writeValueAsString(timestampMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void flushDataToFile(String data) throws IOException {
        var out = new BufferedWriter(new FileWriter(filePath));
        out.write(data);
        out.flush();
    }
}
