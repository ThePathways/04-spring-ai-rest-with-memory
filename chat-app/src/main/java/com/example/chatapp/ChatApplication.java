package com.example.chatapp;

import com.example.chatapp.config.GroqProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Main application class for Chat App.
 * Provides REST API for chat with Spring AI and Groq LLM.
 */
@SpringBootApplication
@EnableConfigurationProperties(GroqProperties.class)
public class ChatApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }
}

