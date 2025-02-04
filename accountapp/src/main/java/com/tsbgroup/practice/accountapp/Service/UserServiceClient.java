package com.tsbgroup.practice.accountapp.Service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class UserServiceClient {
    private final WebClient webClient;

    public UserServiceClient(WebClient.Builder webClientBuilder){
        // replace url with microservice url
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

 public Mono<String> getUserDetails(String userId) {
    return webClient.get()
            .uri("/api/user/{userId}", userId)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> {
                System.out.println("Client error: " + response.statusCode());
                return Mono.error(new RuntimeException("User not found"));
            })
            .onStatus(HttpStatusCode::is5xxServerError, response -> {
                System.out.println("Server error: " + response.statusCode());
                return Mono.error(new RuntimeException("User service failure"));
            })
            .bodyToMono(String.class)
            .doOnSuccess(response -> System.out.println("User API Response: " + response))
            .doOnError(error -> System.out.println("WebClient Error: " + error.getMessage()));
}
}
