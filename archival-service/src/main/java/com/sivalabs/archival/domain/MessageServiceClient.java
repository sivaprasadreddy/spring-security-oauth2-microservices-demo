package com.sivalabs.archival.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageServiceClient {
    private static final Logger log = LoggerFactory.getLogger(MessageServiceClient.class);
    private static final String MESSAGES_SVC_URL = "http://localhost:8181";

    private final SecurityHelper securityHelper;
    private final RestTemplate restTemplate;

    public MessageServiceClient(SecurityHelper securityHelper, RestTemplate restTemplate) {
        this.securityHelper = securityHelper;
        this.restTemplate = restTemplate;
    }

    public void archiveMessages() {
        try {
            String url = MESSAGES_SVC_URL + "/api/messages/archive";
            OAuth2AccessToken oAuth2AccessToken = securityHelper.getOAuth2AccessToken();
            String accessToken = oAuth2AccessToken.getTokenValue();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            HttpEntity<?> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<Void> response = restTemplate.exchange(
                    url, HttpMethod.POST, httpEntity,
                    new ParameterizedTypeReference<>() {});
            log.info("Archive messages response code: {}", response.getStatusCode());
        } catch (Exception e) {
            log.error("Error while invoking Archive messages API", e);
        }
    }
}
