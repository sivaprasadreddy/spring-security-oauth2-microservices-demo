package com.sivalabs.archival.api;

import com.sivalabs.archival.domain.MessageServiceClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
class MessageArchivalController {
    private final MessageServiceClient messageServiceClient;

    MessageArchivalController(MessageServiceClient messageServiceClient) {
        this.messageServiceClient = messageServiceClient;
    }

    @PostMapping("/api/messages/archive")
    Map<String, String> archiveMessages() {
        messageServiceClient.archiveMessages();
        return Map.of("status", "success");
    }
}