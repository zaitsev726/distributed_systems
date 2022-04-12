package ru.nsu.zebra.client.api;

import ru.nsu.zebra.client.dto.database.DatabaseCreateDTO;
import ru.nsu.zebra.client.dto.database.DatabaseResponseDTO;
import ru.nsu.zebra.client.dto.ResponseDTO;
import ru.nsu.zebra.client.dto.database.DatabaseStorageCreateDTO;
import ru.nsu.zebra.client.dto.scan.ScanRequestDTO;
import ru.nsu.zebra.client.dto.scan.ScanResponseDTO;
import ru.nsu.zebra.client.dto.search.SearchRequestDTO;
import ru.nsu.zebra.client.dto.search.SearchResponseDTO;

import java.util.Collection;

public interface DatabaseZebraApiClient {
    ResponseDTO<DatabaseResponseDTO> create(DatabaseCreateDTO createDTO);

    ResponseDTO<Collection<DatabaseResponseDTO>> getAll();

    ResponseDTO<DatabaseResponseDTO> getById(String id);

    ResponseDTO<DatabaseResponseDTO> update(String id, DatabaseCreateDTO updateDTO);

    boolean delete(String id);

    boolean insertStorage(String id, DatabaseStorageCreateDTO storageCreateDTO);

    ResponseDTO<DatabaseResponseDTO> drop(String id);

    ResponseDTO<SearchResponseDTO> search(String id, SearchRequestDTO requestDTO);

    ResponseDTO<ScanResponseDTO> scan(String id, ScanRequestDTO requestDTO);
}
