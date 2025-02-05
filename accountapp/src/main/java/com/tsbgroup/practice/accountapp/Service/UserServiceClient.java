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
        String requestUrl = "http://localhost:8080/api/user/" + userId;
        System.out.println("Calling User API: " + requestUrl);
    
        return webClient.get()
                .uri("/api/user/{userId}", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> 
                    Mono.error(new RuntimeException("User not found"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response -> 
                    Mono.error(new RuntimeException("User service failure"))
                )
                .bodyToMono(String.class)
                .doOnSuccess(response -> System.out.println("User API Response: " + response))
                .doOnError(error -> System.out.println("WebClient Error: " + error.getMessage()));
    }
}
