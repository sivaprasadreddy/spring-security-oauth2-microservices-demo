package com.sivalabs.messages.domain;

import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;

public class Message {
    private Long id;
    @NotEmpty
    private String content;
    @NotEmpty
    private String createdBy;
    private Instant createdAt;

    public Message() {
    }

    public Message(Long id, String content, String createdBy, Instant createdAt) {
        this.id = id;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
