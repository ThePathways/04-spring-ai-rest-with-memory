package com.example.chatapp.factory;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Factory for creating ChatClient instances with or without memory support.
 * Provides centralized ChatClient creation for the application.
 */
@Component
public class ChatClientFactory {
    
    private final ChatModel chatModel;
    
    public ChatClientFactory(@Qualifier("groqChatModel") ChatModel chatModel) {
        this.chatModel = chatModel;
    }
    
    /**
     * Creates a basic ChatClient without memory support.
     * Use this for one-off questions or stateless conversations.
     * @return ChatClient instance
     */
    public ChatClient createChatClient() {
        return ChatClient.builder(chatModel).build();
    }
    
    /**
     * Creates a ChatClient with default system prompt.
     * @return ChatClient instance with default system prompt
     */
    public ChatClient createChatClientWithDefaultSystem() {
        return ChatClient.builder(chatModel)
                .defaultSystem("You are a helpful assistant.")
                .build();
    }
    
    /**
     * Creates a ChatClient with memory support.
     * Uses MessageChatMemoryAdvisor to persist conversation history.
     * @param chatMemory the memory store to use
     * @return ChatClient instance with memory support
     */
    public ChatClient createChatClient(@Qualifier("springAiChatMemory") ChatMemory chatMemory) {
        return ChatClient.builder(chatModel)
                .defaultSystem("You are a helpful assistant.")
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }
    
    /**
     * Creates a ChatClient with session-based memory support.
     * Uses MessageChatMemoryAdvisor to persist conversation history per session.
     * @param chatMemory the memory store to use
     * @param sessionId the session identifier for memory isolation
     * @param maxMessages the maximum number of messages to retain in memory
     * @return ChatClient instance with session-based memory support
     */
    public ChatClient createChatClient(@Qualifier("springAiChatMemory") ChatMemory chatMemory, String sessionId, int maxMessages) {
        return ChatClient.builder(chatModel)
                .defaultSystem("You are a helpful assistant.")
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory, sessionId, maxMessages))
                .build();
    }
    
    /**
     * Gets the ChatModel instance.
     * @return ChatModel instance
     */
    public ChatModel getChatModel() {
        return chatModel;
    }
}

