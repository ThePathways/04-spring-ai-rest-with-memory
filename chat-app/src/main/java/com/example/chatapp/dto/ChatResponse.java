package com.example.chatapp.dto;

import java.util.List;

/**
 * Data transfer object for chat response.
 */
public class ChatResponse {
    
    private String sessionId;
    private String response;
    private List<ChatMessage> history;
    
    public ChatResponse() {
    }
    
    public ChatResponse(String sessionId, String response, List<ChatMessage> history) {
        this.sessionId = sessionId;
        this.response = response;
        this.history = history;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getResponse() {
        return response;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }
    
    public List<ChatMessage> getHistory() {
        return history;
    }
    
    public void setHistory(List<ChatMessage> history) {
        this.history = history;
    }
    
    /**
     * Inner class for chat message.
     */
    public static class ChatMessage {
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
    }
}

