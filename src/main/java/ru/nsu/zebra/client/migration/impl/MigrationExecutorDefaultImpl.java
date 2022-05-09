package ru.nsu.zebra.client.migration.impl;

import ru.nsu.zebra.client.api.RecordAction;
import ru.nsu.zebra.client.api.impl.DatabaseZebraApiClientImpl;
import ru.nsu.zebra.client.dto.QueryType;
import ru.nsu.zebra.client.dto.ResponseDTO;
import ru.nsu.zebra.client.dto.database.DatabaseResponseDTO;
import ru.nsu.zebra.client.dto.database.UpdateRecordRequestDTO;
import ru.nsu.zebra.client.dto.scan.ScanResponseDTO;
import ru.nsu.zebra.client.dto.scan.ScanTermDTO;
import ru.nsu.zebra.client.dto.search.SearchItemDTO;
import ru.nsu.zebra.client.dto.search.SearchRequestDTO;
import ru.nsu.zebra.client.migration.Formatter;
import ru.nsu.zebra.client.migration.MigrationExecutor;
import ru.nsu.zebra.client.migration.TimestampStorage;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class MigrationExecutorDefaultImpl implements MigrationExecutor {
    private final List<DatabaseResponseDTO> databasesToMigrate;
    private final DatabaseResponseDTO targetDatabase;
    private final TimestampStorage timestampStorage;
    private final DatabaseZebraApiClientImpl databaseClient;


    public MigrationExecutorDefaultImpl(List<DatabaseResponseDTO> databasesToMigrate,
                                        DatabaseResponseDTO targetDatabase,
                                        TimestampStorage timestampStorage,
                                        DatabaseZebraApiClientImpl databaseClient) {
        this.databasesToMigrate = databasesToMigrate;
        this.targetDatabase = targetDatabase;
        this.timestampStorage = timestampStorage;
        this.databaseClient = databaseClient;
    }

    public void migrate() {
        for (var database : databasesToMigrate) {
            var databaseTimestamp = timestampStorage.getTimestamp(database.id())
                    .orElse(Instant.parse("1970-01-01T00:00:00Z"));
            var currentTimestamp = Instant.now();

            var createdSearchResult = databaseClient
                    .findAllWithCreatedDateIsAfter(database.id(), databaseTimestamp);
            var modifiedSearchResult = databaseClient
                    .findAllWithModifiedDateIsAfter(database.id(), databaseTimestamp);
            var searchRequest = "";
            if (createdSearchResult.success() && modifiedSearchResult.success()) {
                searchRequest = buildSearchRequest(createdSearchResult, modifiedSearchResult);
            } else if (createdSearchResult.success()) {
                searchRequest = buildSearchRequest(createdSearchResult, true);
            } else if (modifiedSearchResult.success()) {
                searchRequest = buildSearchRequest(modifiedSearchResult, false);
            } else {
                continue;
            }
            var migrationRecordsResponse = databaseClient
                    .search(database.id(), new SearchRequestDTO(
                            QueryType.PQF,
                            searchRequest,
                            null,
                            null,
                            "dc",
                            null
                    ));

            if (!migrationRecordsResponse.success()) {
                timestampStorage.updateTimestamp(database.id(), currentTimestamp);
                continue;
            }

            migrationRecordsResponse.response().records().stream()
                    .map(SearchItemDTO::recordData)
                    .forEach(it -> databaseClient
                            .updateRecord(targetDatabase.id(), buildUpdateRecordRequestDTO(it)));

            timestampStorage.updateTimestamp(database.id(), currentTimestamp);
        }

    }

    private String buildSearchRequest(ResponseDTO<ScanResponseDTO> createdResult, ResponseDTO<ScanResponseDTO> modifiedResponse) {
        return "@or @1=1011 @4=106 " + prepareTimestampQuery(createdResult.response())
                + " @1=1012 @4=106 " + prepareTimestampQuery(modifiedResponse.response());
    }

    private String buildSearchRequest(ResponseDTO<ScanResponseDTO> result, boolean isCreated) {
        var searchQuery = prepareTimestampQuery(result.response());
        if (isCreated) {
            return "@1=1011 @4=106 " + searchQuery;
        }
        return "@1=1012 @4=106 " + searchQuery;
    }

    private String prepareTimestampQuery(ScanResponseDTO response) {
        return response.terms().stream().map(ScanTermDTO::displayTerm)
                .collect(Collectors.joining(" ", "\"", "\""));
    }

    private UpdateRecordRequestDTO buildUpdateRecordRequestDTO(String recordData) {
        return new UpdateRecordRequestDTO(
                recordData,
                RecordAction.UPDATE,
                true
        );
    }

}
