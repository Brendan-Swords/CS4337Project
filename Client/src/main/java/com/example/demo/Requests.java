package com.example.demo;

import com.example.demo.models.Book;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Requests
{
    RestTemplate restTemplate = new RestTemplate();
    final String baseURL = "http://localhost:8080/api";

    Session createAccount()
    {
        // Send request
        // Store user/session data in Session, and return
        return new Session();
    }

    Session signIn()
    {
        // Send request
        // Store user/session data in Session, and return
        return new Session();
    }
    void publish(Session session)
    {
        Book book = new Book();
        HttpEntity<Book> requestEntity = new HttpEntity<>(book);
        ResponseEntity<String> response = restTemplate.exchange(baseURL + "/main/publish/" + session.publisherId + "/PublishBook", HttpMethod.POST, requestEntity, String.class, session.publisherId);
        System.out.println(response.getBody());
    }
    void createOrder(Session session)
    {
        // Prompt for input
        // Send Request and Print Response
    }
    void deleteOrder(Session session)
    {
        // Prompt for input
        // Send Request and Print Response
    }
}
