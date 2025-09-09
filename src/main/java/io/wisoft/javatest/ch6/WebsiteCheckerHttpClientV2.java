package io.wisoft.javatest.ch6;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletionException;
import java.util.function.Consumer;

public class WebsiteCheckerHttpClientV2 {

    private static final String URL = "http://example.com";
    private final HttpClient httpClient;

    public WebsiteCheckerHttpClientV2(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void isWebsiteAliveCallBack(Consumer<WebsiteStatus> callback) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(URL)).GET().build();

        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(this::throwOnInvalidResponse)
                .thenApply(HttpResponse::body)
                .thenAccept(body -> WebsiteCheckerHttpClientV2.processFetchSuccess(body, callback))
                .exceptionally(ex -> {
                    WebsiteCheckerHttpClientV2.processFetchError(ex, callback);
                    return null;
                });
    }

    private HttpResponse<String> throwOnInvalidResponse(HttpResponse<String> response) {
        if (response.statusCode() / 100 != 2) {
            throw new RuntimeException("HTTP " + response.statusCode());
        }
        return response;
    }

    public static void processFetchSuccess(String body, Consumer<WebsiteStatus> callback) {
        if (body != null && body.contains("illustrative")) {
            callback.accept(new WebsiteStatus(true, "ok"));
        } else {
            callback.accept(new WebsiteStatus(false, "missing text"));
        }
    }

    public static void processFetchError(Throwable ex, Consumer<WebsiteStatus> callback) {
        Throwable cause = (ex instanceof CompletionException && ex.getCause() != null) ? ex.getCause() : ex;
        callback.accept(new WebsiteStatus(false, cause.getMessage()));
    }
}
