package ru.nsu.zebra.client.dto.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StorageUpdateDTO(
        @JsonProperty(value = "addinfo")
        String addInfo) {
}
