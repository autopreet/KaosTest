package dev.manpreet.demotests.utils;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Slf4j
public class TestUtils {

    public static int sendHTTPGETRequest(String targetURL, String acceptType) {
        int responseCode = -1;
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(targetURL))
                .header(HttpHeaders.ACCEPT, acceptType)
                .GET()
                .build();
        HttpResponse<InputStream> response;
        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
            responseCode = response.statusCode();
            log.info("Response code - " + responseCode);
        } catch (IOException | InterruptedException e) {
            log.error("Failed to post data to " + targetURL, e);
        }
        return responseCode;
    }

    public static String getHTTPJSONBody(String targetURL) {
        byte[] body = null;
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(targetURL))
                .header(HttpHeaders.ACCEPT, "application/json")
                .GET()
                .build();
        HttpResponse<InputStream> response;
        try {
            response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());
            log.info("Response code - " + response.statusCode());
            body = response.body().readAllBytes();
        } catch (IOException | InterruptedException e) {
            log.error("Failed to post data to " + targetURL, e);
        }
        return new String(body, StandardCharsets.UTF_8);
    }
}