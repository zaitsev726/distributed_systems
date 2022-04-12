package ru.nsu.zebra.client.dto.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StorageCreateDTO(
        @JsonProperty(value = "database_id", required = true)
        String databaseId,
        @JsonProperty(value = "data", required = true)
        byte[] data,
        @JsonProperty(value = "addinfo")
        String addInfo) {
}
