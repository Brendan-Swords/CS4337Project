package cs4337.group6.AuthenticationService.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    // Constructor that initializes the OAuth2AuthorizedClientManager
    public AuthService(ClientRegistrationRepository clientRegistrationRepository,
                       OAuth2AuthorizedClientService authorizedClientService) {
        
        // Create an OAuth2AuthorizedClientProvider that supports multiple grant types
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .refreshToken()
                        .clientCredentials()
                        .build();

        // Initialize AuthorizedClientServiceOAuth2AuthorizedClientManager
        AuthorizedClientServiceOAuth2AuthorizedClientManager clientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService);
        
        // Set the authorized client provider
        clientManager.setAuthorizedClientProvider(authorizedClientProvider);
        this.authorizedClientManager = clientManager;
    }

    // Method to retrieve an access token, which will be refreshed if expired
    public String getAccessToken(Authentication authentication, String clientRegistrationId) {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId(clientRegistrationId)
                .principal(authentication)
                .build();

        OAuth2AuthorizedClient client = authorizedClientManager.authorize(authorizeRequest);

        if (client != null && client.getAccessToken() != null) {
            return client.getAccessToken().getTokenValue();
        }

        throw new RuntimeException("Failed to retrieve or refresh access token");
    }

    // Method to refresh the access token using the provided refresh token
    public String refreshToken(String refreshToken) {
        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("my-client")  // Replace with your actual client registration ID
                .principal(refreshToken)  // Use the refresh token as principal to refresh the token
                .build();

        OAuth2AuthorizedClient client = authorizedClientManager.authorize(authorizeRequest);

        if (client != null && client.getAccessToken() != null) {
            return client.getAccessToken().getTokenValue();
        }

        throw new RuntimeException("Failed to refresh access token");
    }
}
