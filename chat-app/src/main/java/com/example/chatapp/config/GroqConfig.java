package com.example.chatapp.config;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration for Groq API and ChatModel.
 * Uses spring.ai.groq prefix for configuration properties.
 */
@Configuration
public class GroqConfig {
    
    private final GroqProperties properties;
    
    public GroqConfig(GroqProperties properties) {
        this.properties = properties;
    }
    
    /**
     * Creates ChatModel bean for Groq using OpenAiChatModel.
     * Configures model, temperature, and maxTokens from properties.
     */
    @Bean("groqChatModel")
    @Primary
    @ConditionalOnMissingBean
    public ChatModel groqChatModel() {
        OpenAiApi openAiApi = new OpenAiApi(properties.getBaseUrl(), properties.getApiKey());
        
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel(properties.getChatModel())
                .withTemperature((float) properties.getTemperature())
                .withMaxTokens(properties.getMaxTokens())
                .build();
        
        return new OpenAiChatModel(openAiApi, options);
    }
}

