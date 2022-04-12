package ru.nsu.zebra.client.api.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.zebra.client.dto.ResponseDTO;

import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public abstract class ApiClient {
    String DEFAULT_STORAGE_URL = "";
    final HttpClient client = HttpClient
            .newBuilder()
            .proxy(ProxySelector.getDefault())
            .build();
    final ObjectMapper objectMapper = new ObjectMapper();

    HttpRequest builtGetRequest(String destination) throws URISyntaxException {
        return getRequestBuilder(destination)
                .GET()
                .build();
    }

    HttpRequest builtPostRequest(String destination, HttpRequest.BodyPublisher publisher) throws URISyntaxException {
        return getRequestBuilder(destination)
                .setHeader("Content-Type", "application/json")
                .POST(publisher)
                .build();
    }

    HttpRequest builtPutRequest(String destination, HttpRequest.BodyPublisher publisher) throws URISyntaxException {
        return getRequestBuilder(destination)
                .setHeader("Content-Type", "application/json")
                .PUT(publisher)
                .build();
    }

    HttpRequest builtDeleteRequest(String destination) throws URISyntaxException {
        return getRequestBuilder(destination)
                .DELETE()
                .build();
    }

    HttpRequest.Builder getRequestBuilder(String destination) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI("http://localhost:3000/api/v1/" + DEFAULT_STORAGE_URL + destination));
    }
}
