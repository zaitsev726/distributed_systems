package ru.nsu.zebra.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseDTO <T>(
        boolean success,
        @JsonProperty(value = "data")
        T response) {
}
