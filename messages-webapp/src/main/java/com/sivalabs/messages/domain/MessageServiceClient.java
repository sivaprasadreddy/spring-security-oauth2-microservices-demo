package com.sivalabs.messages.domain;

import com.sivalabs.messages.domain.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MessageServiceClient {
    private static final Logger log = LoggerFactory.getLogger(MessageServiceClient.class);
    private static final String MESSAGE_SVC_BASE_URL = "http://localhost:8181";

    private final SecurityHelper securityHelper;
    private final RestTemplate restTemplate;

    public MessageServiceClient(SecurityHelper securityHelper, RestTemplate restTemplate) {
        this.securityHelper = securityHelper;
        this.restTemplate = restTemplate;
    }

    public List<Message> getMessages() {
        try {
            String url = MESSAGE_SVC_BASE_URL + "/api/messages";
            ResponseEntity<List<Message>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {});
            return response.getBody();
        } catch (Exception e) {
            log.error("Error while fetching messages", e);
            return List.of();
        }
    }

    public void createMessage(Message message) {
        try {
            String url = MESSAGE_SVC_BASE_URL + "/api/messages";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + securityHelper.getAccessToken());
            HttpEntity<?> httpEntity = new HttpEntity<>(message, headers);
            ResponseEntity<Message> response = restTemplate.exchange(
                    url, HttpMethod.POST, httpEntity,
                    new ParameterizedTypeReference<>() {});
            log.info("Create message response code: {}", response.getStatusCode());
        } catch (Exception e) {
            log.error("Error while creating message", e);
        }
    }
}
