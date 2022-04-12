package ru.nsu.zebra.client.dto.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RepositoryResponseDTO(
        String id,
        String name,
        String type) {
}
