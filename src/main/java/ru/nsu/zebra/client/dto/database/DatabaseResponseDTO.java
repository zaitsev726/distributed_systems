package ru.nsu.zebra.client.dto.database;

import com.fasterxml.jackson.annotation.*;
import ru.nsu.zebra.client.dto.storage.StorageResponseDTO;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatabaseResponseDTO(
        @JsonProperty(required = true)
        String id,
        @JsonProperty(value = "repository_id", required = true)
        String repositoryId,
        @JsonProperty(required = true)
        String name,
        List<StorageResponseDTO> storages){
}
