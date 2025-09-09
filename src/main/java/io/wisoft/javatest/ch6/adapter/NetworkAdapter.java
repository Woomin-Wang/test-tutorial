package io.wisoft.javatest.ch6.adapter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class NetworkAdapter implements INetworkAdapter {

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public CompletableFuture<NetworkResult> fetchUrlText(String url) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    boolean isSuccess = response.statusCode() / 100 == 2;
                    String responseText = isSuccess ? response.body() : "HTTP " + response.statusCode();
                    return new NetworkResult(isSuccess, responseText);
                })
                .exceptionally(ex -> new NetworkResult(false, ex.getMessage())); // 5. 통신 자체에 실패하면 예외를 결과 객체로 변환합니다.
    }
}
