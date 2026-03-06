package com.example.chatapp.service;

import com.example.chatapp.dto.ChatMessage;
import com.example.chatapp.factory.ChatClientFactory;
import com.example.chatapp.memory.SpringAiChatMemoryAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of LlmChatService that provides chat functionality with memory support.
 * Uses Spring AI's ChatClient with memory advisor for conversation history.
 */
@Service
public class LlmChatServiceImpl implements LlmChatService {
    
    private static final int DEFAULT_MAX_MESSAGES = 100;
    
    private final ChatClientFactory chatClientFactory;
    private final SpringAiChatMemoryAdapter chatMemory;
    
    public LlmChatServiceImpl(ChatClientFactory chatClientFactory, 
                              @Qualifier("springAiChatMemory") SpringAiChatMemoryAdapter chatMemory) {
        this.chatClientFactory = chatClientFactory;
        this.chatMemory = chatMemory;
    }
    
    @Override
    public String chat(String sessionId, String userMessage) {
        // Use ChatClient from factory with session-based memory advisor
        String response = chatClientFactory.createChatClient(chatMemory, sessionId, DEFAULT_MAX_MESSAGES)
                .prompt()
                .user(userMessage)
                .call()
                .content();
        
        return response;
    }
    
    @Override
    public List<ChatMessage> getChatHistory(String sessionId) {
        // Use the adapter to get messages in our custom format
        return chatMemory.getMessages(sessionId);
    }
    
    @Override
    public void clearChatHistory(String sessionId) {
        chatMemory.clear(sessionId);
    }
}

