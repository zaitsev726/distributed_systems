package ru.nsu.zebra.client.dto.database;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nsu.zebra.client.api.RecordAction;

public record UpdateRecordRequestDTO(
        @JsonProperty(required = true)
        String record,
        RecordAction action,
        Boolean commitEnable
) {
}
