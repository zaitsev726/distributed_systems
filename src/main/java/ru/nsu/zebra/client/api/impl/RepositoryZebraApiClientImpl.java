package ru.nsu.zebra.client.api.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.nsu.zebra.client.api.RepositoryZebraApiClient;
import ru.nsu.zebra.client.dto.*;
import ru.nsu.zebra.client.dto.repository.RepositoryCreateDTO;
import ru.nsu.zebra.client.dto.repository.RepositoryResponseDTO;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;

public class RepositoryZebraApiClientImpl extends ApiClient implements RepositoryZebraApiClient {

    public RepositoryZebraApiClientImpl() {
        this.DEFAULT_STORAGE_URL = "repositories";
    }

    @Override
    public ResponseDTO<RepositoryResponseDTO> create(RepositoryCreateDTO createDTO) {
        try {
            var requestBody = objectMapper.writeValueAsString(createDTO);
            var request = getRequestBuilder("")
                    .setHeader("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultResponse();
    }

    @Override
    public ResponseDTO<Collection<RepositoryResponseDTO>> getAll() {
        try {
            var request = builtGetRequest("");

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDTO<>(false, List.of());
    }

    @Override
    public ResponseDTO<RepositoryResponseDTO> getById(String id) {
        try {
            var request = builtGetRequest("/" + id);

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultResponse();
    }

    @Override
    public ResponseDTO<RepositoryResponseDTO> update(String id, RepositoryCreateDTO updateDTO) {
        try {
            var requestBody = objectMapper.writeValueAsString(updateDTO);
            var request = builtPutRequest("/" + id, HttpRequest.BodyPublishers.ofString(requestBody));

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //TODO -----------
            var parsed =  objectMapper.readValue(response.body(), new TypeReference<>() {});
            return (ResponseDTO<RepositoryResponseDTO>) parsed;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultResponse();
    }

    @Override
    public boolean delete(String id) {
        try {
            var request = builtDeleteRequest("/" + id);
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var parsed = objectMapper.readValue(response.body(), new TypeReference<ResponseDTO>() {});
            return parsed.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ResponseDTO<RepositoryResponseDTO> init(String id) {
        try {
            var request = builtPostRequest("/" + id + "/init", HttpRequest.BodyPublishers.noBody());
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultResponse();
    }

    @Override
    public ResponseDTO<RepositoryResponseDTO> commit(String id) {
        try {
            var request = builtPostRequest("/" + id + "/commit", HttpRequest.BodyPublishers.noBody());
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultResponse();
    }

    @Override
    public ResponseDTO<RepositoryResponseDTO> clean(String id) {
        try {
            var request = builtPostRequest("/" + id + "/clean", HttpRequest.BodyPublishers.noBody());
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultResponse();
    }

    @Override
    public ResponseDTO<Integer> count() {
        return null;
    }

    private ResponseDTO<RepositoryResponseDTO> defaultResponse() {
        return new ResponseDTO<>(false, new RepositoryResponseDTO("", "", ""));
    }
}
