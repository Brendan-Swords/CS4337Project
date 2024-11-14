package cs4337.group6.ApiGateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/main")
public class ApiGateway {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ApiGateway(@Value("${api.base-url}") String baseUrl) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
    }

    @PostMapping("/auth")
    public ResponseEntity<String> authRequest(@RequestBody String request) {
        try {
            return restTemplate.postForEntity(baseUrl + "/auth", request, String.class);
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Failed to reach auth service: " + e.getMessage());
        }
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publishRequest(@RequestBody String request) {
        try {
            return restTemplate.postForEntity(baseUrl + "/publish", request, String.class);
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Failed to reach publish service: " + e.getMessage());
        }
    }

    @PostMapping("/catalog")
    public ResponseEntity<String> catalogRequest(@RequestBody String request) {
        try {
            return restTemplate.postForEntity(baseUrl + "/catalog", request, String.class);
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Failed to reach catalog service: " + e.getMessage());
        }
    }

    @PostMapping("/order")
    public ResponseEntity<String> orderRequest(@RequestBody String request) {
        try {
            return restTemplate.postForEntity(baseUrl + "/order", request, String.class);
        } catch (RestClientException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Failed to reach order service: " + e.getMessage());
        }
    }
}
