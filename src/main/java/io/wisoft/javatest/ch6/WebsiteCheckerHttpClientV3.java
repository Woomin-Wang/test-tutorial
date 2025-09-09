package io.wisoft.javatest.ch6;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class WebsiteCheckerHttpClientV3 {

    private static final String URL = "http://example.com";
    private final HttpClient httpClient;

    public WebsiteCheckerHttpClientV3(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CompletableFuture<WebsiteStatus> isWebsiteAliveAsyncAwait() {
        HttpRequest request = HttpRequest.newBuilder(URI.create(URL)).GET().build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(WebsiteCheckerHttpClientV3::validateAndGetBody)
                .thenApply(WebsiteCheckerHttpClientV3::processFetchSuccess)
                .exceptionally(WebsiteCheckerHttpClientV3::processFetchError);
    }

    public static String validateAndGetBody(HttpResponse<String> response) {
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException("HTTP " + response.statusCode());
        }
        return response.body();
    }

    public static WebsiteStatus processFetchSuccess(String body) {
        if (body.contains("illustrative")) {
            return new WebsiteStatus(true, "ok");
        }
        return new WebsiteStatus(false, "missing text");
    }

    public static WebsiteStatus processFetchError(Throwable ex) {
        Throwable cause = (ex instanceof CompletionException && ex.getCause() != null) ? ex.getCause() : ex;
        return new WebsiteStatus(false, cause.getMessage());
    }
}
