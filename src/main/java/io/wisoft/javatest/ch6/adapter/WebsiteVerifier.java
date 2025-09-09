package io.wisoft.javatest.ch6.adapter;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class WebsiteVerifier {

    private final INetworkAdapter networkAdapter;

    public WebsiteVerifier(INetworkAdapter networkAdapter) {
        this.networkAdapter = networkAdapter;
    }

    public CompletableFuture<WebsiteStatus> isWebsiteAlive() {

        return networkAdapter.fetchUrlText("http://example.com")
                .thenApply(netResult -> {
                    if (!netResult.ok()) {
                        throw new RuntimeException(netResult.text());
                    }
                    return processFetchSuccess(netResult.text());
                })
                .exceptionally(this::processFetchFail);
    }

    private WebsiteStatus processFetchSuccess(String text) {
        if (text != null && text.contains("illustrative")) {
            return new WebsiteStatus(true, "ok");
        }
        return new WebsiteStatus(false, "missing text");
    }

    private WebsiteStatus processFetchFail(Throwable ex) {
        Throwable cause = (ex instanceof CompletionException && ex.getCause() != null) ? ex.getCause() : ex;
        return new WebsiteStatus(false, cause.getMessage());
    }
}
