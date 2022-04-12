package ru.nsu.zebra.client.dto.scan;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ScanTermDTO(
        @JsonProperty(required = true)
        Integer numberOfRecords,
        @JsonProperty(required = true)
        String value,
        @JsonProperty(required = true)
        String displayTerm) {
}
