package ru.nsu.zebra.client;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StorageClient {
    public void a() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:3000/api/v1/storages"))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient
                .newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(request);
    }
}
