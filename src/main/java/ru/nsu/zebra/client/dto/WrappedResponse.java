package ru.nsu.zebra.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WrappedResponse <T>(
        boolean success,
        @JsonProperty(value = "data")
        ResponseDTO<T> response) {
}