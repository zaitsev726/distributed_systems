package ru.nsu.zebra.client.api.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.client.utils.URIBuilder;
import ru.nsu.zebra.client.api.DatabaseZebraApiClient;
import ru.nsu.zebra.client.dto.ResponseDTO;
import ru.nsu.zebra.client.dto.WrappedResponse;
import ru.nsu.zebra.client.dto.database.DatabaseCreateDTO;
import ru.nsu.zebra.client.dto.database.DatabaseResponseDTO;
import ru.nsu.zebra.client.dto.database.DatabaseStorageCreateDTO;
import ru.nsu.zebra.client.dto.scan.ScanRequestDTO;
import ru.nsu.zebra.client.dto.scan.ScanResponseDTO;
import ru.nsu.zebra.client.dto.search.SearchRequestDTO;
import ru.nsu.zebra.client.dto.search.SearchResponseDTO;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;

public class DatabaseZebraApiClientImpl extends ApiClient implements DatabaseZebraApiClient {

    public DatabaseZebraApiClientImpl() {
        this.DEFAULT_STORAGE_URL = "databases";
    }

    @Override
    public ResponseDTO<DatabaseResponseDTO> create(DatabaseCreateDTO createDTO) {
        try {
            var requestBody = objectMapper.writeValueAsString(createDTO);
            var request = builtPostRequest("", HttpRequest.BodyPublishers.ofString(requestBody));

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultResponse();
    }

    @Override
    public ResponseDTO<Collection<DatabaseResponseDTO>> getAll() {
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
    public ResponseDTO<DatabaseResponseDTO> getById(String id) {
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
    public ResponseDTO<DatabaseResponseDTO> update(String id, DatabaseCreateDTO updateDTO) {
        try {
            var requestBody = objectMapper.writeValueAsString(updateDTO);
            var request = builtPutRequest("/" + id, HttpRequest.BodyPublishers.ofString(requestBody));

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //TODO -----------
            var parsed = objectMapper.readValue(response.body(), new TypeReference<>() {
            });
            return (ResponseDTO<DatabaseResponseDTO>) parsed;
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
            var parsed = objectMapper.readValue(response.body(), new TypeReference<ResponseDTO>() {
            });
            return parsed.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertStorage(String id, DatabaseStorageCreateDTO storageCreateDTO) {
        try {
            var request = builtPostRequest("/" + id + "/update/" + storageCreateDTO.storageId(),
                    HttpRequest.BodyPublishers.noBody());
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var parsed = objectMapper.readValue(response.body(), new TypeReference<ResponseDTO>() {
            });
            return parsed.success();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ResponseDTO<DatabaseResponseDTO> drop(String id) {
        try {
            var request = builtPostRequest("/" + id + "/drop",
                    HttpRequest.BodyPublishers.noBody());
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultResponse();
    }

    @Override
    public ResponseDTO<SearchResponseDTO> search(String id, SearchRequestDTO requestDTO) {
        try {
            var uri =  new URIBuilder("http://localhost:3000/api/v1/databases/" + id + "/search")
                    .addParameters(requestDTO.convertToParamList())
                    .build();
            var request = HttpRequest.newBuilder().uri(uri).GET().build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var wrappedResponse = objectMapper.readValue(response.body(),
                    new TypeReference<WrappedResponse<SearchResponseDTO>>() {});
            return wrappedResponse.response();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseDTO<ScanResponseDTO> scan(String id, ScanRequestDTO requestDTO) {
        try {
            var uri =  new URIBuilder("http://localhost:3000/api/v1/databases/" + id + "/scan")
                    .addParameters(requestDTO.convertToParamList())
                    .build();
            var request = HttpRequest.newBuilder().uri(uri).GET().build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var wrappedResponse = objectMapper.readValue(response.body(),
                    new TypeReference<WrappedResponse<ScanResponseDTO>>() {});
            return wrappedResponse.response();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResponseDTO<DatabaseResponseDTO> defaultResponse() {
        return new ResponseDTO<>(false, new DatabaseResponseDTO("", "", "", List.of()));
    }
}
