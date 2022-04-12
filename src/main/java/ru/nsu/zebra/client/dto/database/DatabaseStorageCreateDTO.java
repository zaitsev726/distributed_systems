package ru.nsu.zebra.client.dto.database;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DatabaseStorageCreateDTO(
        @JsonProperty(value = "storage_id", required = true)
        String storageId) {
}
