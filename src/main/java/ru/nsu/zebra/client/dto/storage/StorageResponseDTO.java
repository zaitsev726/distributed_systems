package ru.nsu.zebra.client.dto.storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StorageResponseDTO(
        @JsonProperty(required = true)
        String id,
        @JsonProperty(value = "database_id", required = true)
        String databaseId,
        @JsonProperty(value = "uuidfilename", required = true)
        String uuidFileName,
        @JsonProperty(value = "filename", required = true)
        String fileName,
        @JsonProperty(value = "filesize", required = true)
        Integer fileSize,
        @JsonProperty(value = "addinfo")
        String addInfo) {
}
