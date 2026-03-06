package com.example.chatapp.service;

import com.example.chatapp.dto.ChatMessage;
import java.util.List;

/**
 * Interface for LLM chat service with memory support.
 * Provides methods for sending chat messages and managing conversation history.
 */
public interface LlmChatService {
    
    /**
     * Send a chat message and get a response from the LLM.
     * @param sessionId the chat session identifier
     * @param userMessage the user's message
     * @return the AI's response as a String
     */
    String chat(String sessionId, String userMessage);
    
    /**
     * Get chat history for a specific session.
     * @param sessionId the chat session identifier
     * @return list of messages
     */
    List<ChatMessage> getChatHistory(String sessionId);
    
    /**
     * Clear chat history for a specific session.
     * @param sessionId the chat session identifier
     */
    void clearChatHistory(String sessionId);
}

