package com.sivalabs.messages.api;

import com.sivalabs.messages.domain.Message;
import com.sivalabs.messages.domain.MessageRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
class MessageController {
    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    private final MessageRepository messageRepository;

    MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    List<Message> getMessages() {
        return messageRepository.getMessages();
    }

    @PostMapping
    Message createMessage(@RequestBody @Valid Message message) {
        return messageRepository.createMessage(message);
    }

    @PostMapping("/archive")
    Map<String,String> archiveMessages() {
        log.info("Archiving all messages");
        return Map.of("status", "success");
    }
}
