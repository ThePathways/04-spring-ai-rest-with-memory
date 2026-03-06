package com.example.chatapp.memory;

import com.example.chatapp.dto.ChatMessage;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Session-based ChatMemory implementation using Spring AI's ChatMemory interface.
 * Supports multiple sessions with concurrent access.
 */
@Component("springAiChatMemory")
public class SpringAiChatMemoryAdapter implements ChatMemory {
    
    private final ConcurrentHashMap<String, List<Message>> chatHistory = new ConcurrentHashMap<>();
    
    @Override
    public void add(String sessionId, List<Message> messages) {
        chatHistory.computeIfAbsent(sessionId, k -> new ArrayList<>()).addAll(messages);
    }
    
    @Override
    public List<Message> get(String sessionId, int lastN) {
        List<Message> messages = chatHistory.getOrDefault(sessionId, new ArrayList<>());
        int size = messages.size();
        if (lastN >= size) {
            return new ArrayList<>(messages);
        }
        return new ArrayList<>(messages.subList(size - lastN, size));
    }
    
    @Override
    public void clear(String sessionId) {
        chatHistory.remove(sessionId);
    }
    
    /**
     * Get all messages for a session converted to custom ChatMessage format.
     * @param sessionId the chat session identifier
     * @return list of messages in custom ChatMessage format
     */
    public List<ChatMessage> getMessages(String sessionId) {
        List<Message> messages = chatHistory.getOrDefault(sessionId, new ArrayList<>());
        List<ChatMessage> result = new ArrayList<>();
        for (Message msg : messages) {
            String role = msg.getMessageType().name().toLowerCase();
            String content = msg.toString();
            result.add(new ChatMessage(role, content));
        }
        return result;
    }
}

