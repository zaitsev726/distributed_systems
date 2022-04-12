package ru.nsu.zebra.client.dto.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchDiagnosticDTO(
        @JsonProperty(required = true)
        String message,
        @JsonProperty(required = true)
        String details) {
}
