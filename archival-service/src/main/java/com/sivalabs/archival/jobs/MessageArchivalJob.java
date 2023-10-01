package com.sivalabs.archival.jobs;

import com.sivalabs.archival.domain.MessageServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MessageArchivalJob {
    private static final Logger log = LoggerFactory.getLogger(MessageArchivalJob.class);

    private final MessageServiceClient messageServiceClient;

    public MessageArchivalJob(MessageServiceClient messageServiceClient) {
        this.messageServiceClient = messageServiceClient;
    }

    @Scheduled(fixedDelay = 30000)
    public void run() {
      log.info("Running MessageArchivalJob at {}", Instant.now());
        messageServiceClient.archiveMessages();
    }
}
