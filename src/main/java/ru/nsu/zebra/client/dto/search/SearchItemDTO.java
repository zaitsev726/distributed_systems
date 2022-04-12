package ru.nsu.zebra.client.dto.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchItemDTO(
        @JsonProperty(required = true)
        Integer recordPosition,
        @JsonProperty(required = true)
        String recordIdentifier,
        @JsonProperty(required = true)
        String recordSchema,
        @JsonProperty(required = true)
        String recordData) {
}
