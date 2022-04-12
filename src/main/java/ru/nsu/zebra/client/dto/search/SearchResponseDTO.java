package ru.nsu.zebra.client.dto.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchResponseDTO(
        boolean success,
        @JsonProperty(required = true)
        Integer numberOfRecords,
        @JsonProperty(value = "records")
        List<SearchItemDTO> records,
        @JsonProperty(value = "diagnostics")
        List<SearchDiagnosticDTO> diagnostics) {

}
