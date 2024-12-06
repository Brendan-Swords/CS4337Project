package cs4337.group6.PublishingService;

import cs4337.group6.PublishingService.Models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PublishingServiceIntegrationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void testGetUserByUsername() {

        // Tests if the GET endpoint for retrieving a user by username returns the correct response with HTTP 200 OK.
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Request", "true");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://publishing-service:8080/publishing/Books",
                HttpMethod.GET,
                entity,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRegisterUser() {

        // Tests if the POST endpoint for registering a new user successfully returns the created user's details with HTTP 201 CREATED.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String newUserJson = "{ \"username\": \"user\", \"password\": \"password\" }";
        HttpEntity<String> request = new HttpEntity<>(newUserJson, headers);

        ResponseEntity<User> response = restTemplate.postForEntity(
                "http://publishing-service:8080/publishing/register",
                request,
                User.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("user", response.getBody().getUsername());
        assertEquals("password", response.getBody().getPassword());
    }
}
