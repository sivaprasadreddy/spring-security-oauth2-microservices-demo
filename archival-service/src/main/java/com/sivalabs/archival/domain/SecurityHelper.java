package com.sivalabs.archival.domain;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelper {
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public SecurityHelper(OAuth2AuthorizedClientManager authorizedClientManager) {
        this.authorizedClientManager = authorizedClientManager;
    }

    public OAuth2AccessToken getOAuth2AccessToken() {
        String clientRegistrationId = "archival-service";
        OAuth2AuthorizeRequest authorizeRequest =
                OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationId)
                        // This principal value is unnecessary, but if you don't give it a value,
                        // it throws an exception.
                        .principal("dummy")
                        .build();
        OAuth2AuthorizedClient authorizedClient =
                this.authorizedClientManager.authorize(authorizeRequest);
        return authorizedClient.getAccessToken();
    }
}