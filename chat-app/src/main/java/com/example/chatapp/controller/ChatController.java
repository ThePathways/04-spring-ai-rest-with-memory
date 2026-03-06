package com.example.chatapp.controller;

import com.example.chatapp.dto.ChatRequest;
import com.example.chatapp.dto.ChatResponse;
import com.example.chatapp.dto.ChatMessage;
import com.example.chatapp.service.LlmChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for chat endpoints.
 * Provides HTTP endpoints for chat operations.
 */
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    private final LlmChatService chatService;
    
    public ChatController(LlmChatService chatService) {
        this.chatService = chatService;
    }
    
    /**
     * Send a chat message and get a response.
     * @param request the chat request containing sessionId and message
     * @return ChatResponse with the AI response and chat history
     */
    @PostMapping
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        String sessionId = request.getSessionId();
        if (sessionId == null || sessionId.isBlank()) {
            sessionId = UUID.randomUUID().toString();
        }
        
        String response = chatService.chat(sessionId, request.getMessage());
        
        List<ChatMessage> history = chatService.getChatHistory(sessionId);
        
        ChatResponse chatResponse = new ChatResponse(
                sessionId,
                response,
                convertHistory(history)
        );
        
        return ResponseEntity.ok(chatResponse);
    }
    
    /**
     * Get chat history for a specific session.
     * @param sessionId the chat session identifier
     * @return list of chat messages
     */
    @GetMapping("/history/{sessionId}")
    public ResponseEntity<List<ChatResponse.ChatMessage>> getHistory(@PathVariable String sessionId) {
        List<ChatMessage> history = chatService.getChatHistory(sessionId);
        return ResponseEntity.ok(convertHistory(history));
    }
    
    /**
     * Clear chat history for a specific session.
     * @param sessionId the chat session identifier
     * @return success message
     */
    @DeleteMapping("/history/{sessionId}")
    public ResponseEntity<String> clearHistory(@PathVariable String sessionId) {
        chatService.clearChatHistory(sessionId);
        return ResponseEntity.ok("Chat history cleared for session: " + sessionId);
    }
    
    private List<ChatResponse.ChatMessage> convertHistory(List<ChatMessage> messages) {
        return messages.stream()
                .map(msg -> new ChatResponse.ChatMessage(msg.getRole(), msg.getContent()))
                .collect(Collectors.toList());
    }
}
