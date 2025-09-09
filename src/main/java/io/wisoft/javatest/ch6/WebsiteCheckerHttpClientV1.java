package io.wisoft.javatest.ch6;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class WebsiteCheckerHttpClientV1 {

    private static final String URL = "http://example.com";
    private final HttpClient httpClient;

    public WebsiteCheckerHttpClientV1(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /*
        Callback 사용
    */
    public void isWebsiteAliveCallBack(Consumer<WebsiteStatus> callback) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(URL)).GET().build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() / 100 != 2) {
                        throw new RuntimeException("HTTP " + response.statusCode());
                    }
                    return response.body();
                })
                .thenApply(body -> {
                    if (body.contains("illustrative")) {
                        return new WebsiteStatus(true, "ok");
                    } else {
                        return new WebsiteStatus(false, "text missing");
                    }
                })
                .exceptionally(ex -> {
                    return new WebsiteStatus(false, ex.getMessage());
                })
                .thenAccept(callback);
    }

    /*
        CompletableFuture 반환 (async/await 유사)
    */
    public CompletableFuture<WebsiteStatus> isWebsiteAliveAsyncAwait() {
        HttpRequest request = HttpRequest.newBuilder(URI.create(URL)).GET().build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() / 100 != 2) {
                        throw new RuntimeException("HTTP " + response.statusCode());
                    }
                    return response.body();
                })
                .thenApply(body -> {
                    if (body.contains("illustrative")) {
                        return new WebsiteStatus(true, "ok");
                    }
                    throw new RuntimeException("text missing");
                })
                .exceptionally(ex -> {
                    return new WebsiteStatus(false, ex.getMessage());
                });
    }
}

