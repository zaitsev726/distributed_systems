package ru.nsu.zebra.client.api;

import ru.nsu.zebra.client.dto.*;
import ru.nsu.zebra.client.dto.repository.RepositoryCreateDTO;
import ru.nsu.zebra.client.dto.repository.RepositoryResponseDTO;

import java.util.Collection;

public interface RepositoryZebraApiClient {
    ResponseDTO<RepositoryResponseDTO> create(RepositoryCreateDTO createDTO);

    ResponseDTO<Collection<RepositoryResponseDTO>> getAll();

    ResponseDTO<RepositoryResponseDTO> getById(String id);

    ResponseDTO<RepositoryResponseDTO> update(String id, RepositoryCreateDTO updateDTO);

    boolean delete(String id);

    ResponseDTO<RepositoryResponseDTO> init(String id);

    ResponseDTO<RepositoryResponseDTO> commit(String id);

    ResponseDTO<RepositoryResponseDTO> clean(String id);

    ResponseDTO<Integer> count();
}
