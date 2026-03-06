package com.example.chatapp.dto;

/**
 * Data transfer object representing a chat message.
 */
public class ChatMessage {
    
    private String role;
    private String content;
    
    public ChatMessage() {
    }
    
    public ChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * Factory method to create a user message.
     */
    public static ChatMessage user(String content) {
        return new ChatMessage("user", content);
    }
    
    /**
     * Factory method to create an assistant message.
     */
    public static ChatMessage assistant(String content) {
        return new ChatMessage("assistant", content);
    }
}

