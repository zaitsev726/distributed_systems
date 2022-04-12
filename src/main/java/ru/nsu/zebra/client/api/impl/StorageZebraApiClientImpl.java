package ru.nsu.zebra.client.api.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import ru.nsu.zebra.client.api.StorageZebraApiClient;
import ru.nsu.zebra.client.dto.*;
import ru.nsu.zebra.client.dto.repository.RepositoryResponseDTO;
import ru.nsu.zebra.client.dto.storage.StorageCreateDTO;
import ru.nsu.zebra.client.dto.storage.StorageResponseDTO;
import ru.nsu.zebra.client.dto.storage.StorageUpdateDTO;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class StorageZebraApiClientImpl extends ApiClient implements StorageZebraApiClient {

    public StorageZebraApiClientImpl() {
        this.DEFAULT_STORAGE_URL = "storages";
    }

    @Override
    public ResponseDTO<StorageResponseDTO> create(String databaseId, File data) {
        try {
            byte[] fileContent = Files.readAllBytes(data.toPath());

//            var createData = new StorageCreateDTO(databaseId, fileContent, "");
            String boundary = "-------------oiawn4tp89n4e9p5";

            var createData = new HashMap<Object, Object>();
            createData.put("database_id", databaseId);
            createData.put("data", data.toPath());

//            var requestBody = objectMapper.writeValueAsString(createData);
            var request = getRequestBuilder("")
                    .setHeader("Content-Type", "multipart/form-data; boundary=" + boundary)
//                    .setHeader("Content-Disposition", data.getName())
                    .POST(oMultipartData(createData, boundary))
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), new TypeReference<>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultResponse();
    }

    @Override
    public ResponseDTO<StorageResponseDTO> create(String databaseId, File data, String additionalInfo) {
        try {
            byte[] fileContent = Files.readAllBytes(data.toPath());

            var createData = new StorageCreateDTO(databaseId, fileContent, additionalInfo);

            var requestBody = objectMapper.writeValueAsString(createData);
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
    public ResponseDTO<Collection<StorageResponseDTO>> getAll() {
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
    public ResponseDTO<StorageResponseDTO> getById(String id) {
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
    public ResponseDTO<StorageResponseDTO> update(String id, StorageUpdateDTO storageUpdateDTO) {
        try {
            var requestBody = objectMapper.writeValueAsString(storageUpdateDTO);
            var request = builtPutRequest("/" + id, HttpRequest.BodyPublishers.ofString(requestBody));

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //TODO -----------
            var parsed =  objectMapper.readValue(response.body(), new TypeReference<>() {});
            return (ResponseDTO<StorageResponseDTO>) parsed;
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
    public String download(String id) {
        try {
            var request = builtGetRequest("/" + id + "/download");

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private ResponseDTO<StorageResponseDTO> defaultResponse() {
        return new ResponseDTO<>(false, new StorageResponseDTO("", "", "", "", 0, ""));
    }

    public static HttpRequest.BodyPublisher oMultipartData(Map<Object, Object> data,
                                                           String boundary) throws IOException {
        var byteArrays = new ArrayList<byte[]>();
        byte[] separator = ("--" + boundary
                + "\r\nContent-Disposition: form-data; name=")
                .getBytes(StandardCharsets.UTF_8);
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            byteArrays.add(separator);

            if (entry.getValue() instanceof Path) {
                var path = (Path) entry.getValue();
                String mimeType = Files.probeContentType(path);
                byteArrays.add(("\"" + entry.getKey() + "\"; filename=\""
                        + path.getFileName() + "\"\r\nContent-Type: " + mimeType
                        + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                byteArrays.add(Files.readAllBytes(path));
                byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(
                        ("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue()
                                + "\r\n").getBytes(StandardCharsets.UTF_8));
            }
        }
        byteArrays
                .add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }
}