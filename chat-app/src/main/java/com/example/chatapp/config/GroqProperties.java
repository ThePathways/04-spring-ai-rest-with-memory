package com.example.chatapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Groq API.
 * Maps to spring.ai.groq prefix in application.properties.
 */
@ConfigurationProperties(prefix = "spring.ai.groq")
public class GroqProperties {
    
    private String apiKey;
    private String baseUrl = "https://api.groq.com/openai";
    private String chatModel = "llama-3.3-70b-versatile";
    private double temperature = 0.7;
    private int maxTokens = 2048;
    
    public String getApiKey() {
        return apiKey;
    }
    
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public String getBaseUrl() {
        return baseUrl;
    }
    
    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    
    public String getChatModel() {
        return chatModel;
    }
    
    public void setChatModel(String chatModel) {
        this.chatModel = chatModel;
    }
    
    public double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
    public int getMaxTokens() {
        return maxTokens;
    }
    
    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }
}

