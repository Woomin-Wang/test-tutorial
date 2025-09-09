package io.wisoft.javatest.ch6.adapter.functional;

import io.wisoft.javatest.ch6.adapter.INetworkAdapter;
import io.wisoft.javatest.ch6.adapter.WebsiteStatus;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class WebsiteVerifierFunctional {

    public CompletableFuture<WebsiteStatus> isWebsiteAlive(INetworkAdapter networkAdapter) {

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
