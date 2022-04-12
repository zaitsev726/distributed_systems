package ru.nsu.zebra.client.dto.database;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DatabaseCreateDTO(
        @JsonProperty(value = "repository_id", required = true)
        String repositoryId,
        @JsonProperty(required = true)
        String name) {
}
