package group6.authentication.service;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;

@Service
public class AuthService {

    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public AuthService(ClientRegistrationRepository clientRegistrationRepository,
                       OAuth2AuthorizedClientRepository authorizedClientRepository) {
        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .refreshToken()
                        .build();

        this.authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);
        this.authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
    }

    // Method to retrieve an access token, which will be refreshed if expired
    public String getAccessToken(Authentication authentication, String clientRegistrationId) {
        OAuth2AuthorizedClient client = authorizedClientManager.authorize(
                OAuth2AuthorizationContext.withClientRegistrationId(clientRegistrationId)
                        .principal(authentication)
                        .build()
        );

        if (client != null && client.getAccessToken() != null) {
            return client.getAccessToken().getTokenValue();
        }

        throw new RuntimeException("Failed to retrieve or refresh access token");
    }
}
