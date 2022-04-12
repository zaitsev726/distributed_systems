package ru.nsu.zebra.client.api;

import ru.nsu.zebra.client.dto.ResponseDTO;
import ru.nsu.zebra.client.dto.storage.StorageResponseDTO;
import ru.nsu.zebra.client.dto.storage.StorageUpdateDTO;

import java.io.File;
import java.util.Collection;

public interface StorageZebraApiClient {
    ResponseDTO<StorageResponseDTO> create(String databaseId, File data);

    ResponseDTO<StorageResponseDTO> create(String databaseId, File data, String additionalInfo);

    ResponseDTO<Collection<StorageResponseDTO>> getAll();

    ResponseDTO<StorageResponseDTO> getById(String id);

    ResponseDTO<StorageResponseDTO> update(String id, StorageUpdateDTO storageUpdateDTO);

    boolean delete(String id);

    String download(String id);
}
