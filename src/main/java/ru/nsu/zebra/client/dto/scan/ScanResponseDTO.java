package ru.nsu.zebra.client.dto.scan;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ScanResponseDTO(
        @JsonProperty(value = "terms")
        List<ScanTermDTO> terms,
        @JsonProperty(value = "diagnostics")
        List<ScanDiagnosticDTO> diagnostics) {
}
