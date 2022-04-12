package ru.nsu.zebra.client.dto.scan;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ScanDiagnosticDTO(
        @JsonProperty(required = true)
        String message,
        @JsonProperty(required = true)
        String details) {
}
