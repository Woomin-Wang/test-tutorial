package io.wisoft.javatest.ch6.adapter;

import java.util.concurrent.CompletableFuture;

public interface INetworkAdapter {
    CompletableFuture<NetworkResult> fetchUrlText(String url);
}
