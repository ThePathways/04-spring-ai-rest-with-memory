# 04-spring-ai-rest-with-memory

# Spring AI Chat Application with Groq LLM

A REST API application built with Spring Boot and Spring AI that provides chat functionality with conversation memory using the Groq LLM (via OpenAI-compatible API).

## Features

- **Chat API**: Send messages and receive AI responses
- **Session-based Conversation Memory**: Maintains chat history per session using Spring AI's ChatMemory
- **Groq LLM Integration**: Uses Groq's high-performance inference API
- **RESTful Endpoints**: Standard HTTP methods for chat operations

## Tech Stack

| Technology | Version |
|------------|---------|
| Spring Boot | 3.4.3 |
| Spring AI | 1.0.0-M1 |
| Java | 21+ |
| Groq API | LLama 3.3 70B Versatile |

## Architecture

### Request Flow

```
HTTP Request
    │
    ▼
┌─────────────────────┐
│  ChatController    │  @RestController
│  (/api/chat)       │  - Handles HTTP requests
└─────────┬───────────┘
          │
          ▼
┌─────────────────────┐
│  LlmChatService    │  @Service Interface
│  (business logic)  │  - Defines chat operations
└─────────┬───────────┘
          │
          ▼
┌─────────────────────┐
│ LlmChatServiceImpl  │  @Service Implementation
│                     │  - Uses ChatClientFactory
└─────────┬───────────┘
          │
          ▼
┌─────────────────────┐
│ ChatClientFactory   │  @Component
│                     │  - Creates ChatClient
│                     │  - Adds MemoryAdvisor
└─────────┬───────────┘
          │
          ▼
┌─────────────────────┐
│    ChatClient       │  Spring AI
│  + MemoryAdvisor    │  - Sends prompts to LLM
└─────────┬───────────┘  - Manages conversation history
          │
          ▼
┌─────────────────────┐
│  OpenAiChatModel   │  ChatModel (Groq)
│  (GroqChatModel)   │  - API call to Groq
└─────────────────────┘
```

### Project Structure

```
04-spring-ai-rest-with-memory/
├── chat-app/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/example/chatapp/
│       │   ├── ChatApplication.java              # Main application class
│       │   ├── config/
│       │   │   ├── GroqConfig.java              # Creates GroqChatModel bean
│       │   │   └── GroqProperties.java         # @ConfigurationProperties
│       │   ├── controller/
│       │   │   └── ChatController.java        # REST endpoints (HTTP layer)
│       │   ├── dto/
│       │   │   ├── ChatRequest.java           # Request DTO
│       │   │   ├── ChatResponse.java          # Response DTO
│       │   │   └── ChatMessage.java           # Message DTO
│       │   ├── factory/
│       │   │   └── ChatClientFactory.java     # Creates ChatClient with memory
│       │   ├── memory/
│       │   │   └── SpringAiChatMemoryAdapter.java  # ChatMemory impl
│       │   └── service/
│       │       ├── LlmChatService.java        # Service interface
│       │       └── LlmChatServiceImpl.java    # Service implementation
│       └── resources/
│           └── application.properties           # Application configuration
└── mvnw, mvnw.cmd                            # Maven wrapper
```

### Component Responsibilities

| Component | Responsibility |
|-----------|----------------|
| `ChatController` | Receives HTTP requests, delegates to LlmChatService |
| `LlmChatService` | Interface defining chat operations |
| `LlmChatServiceImpl` | Implements business logic, orchestrates components |
| `ChatClientFactory` | Creates ChatClient instances with/without memory |
| `ChatClient` | Spring AI client for LLM interaction |
| `MessageChatMemoryAdvisor` | Spring AI advisor for conversation memory |
| `SpringAiChatMemoryAdapter` | Custom ChatMemory implementation (in-memory) |
| `GroqConfig` | Creates GroqChatModel bean |
| `GroqProperties` | Holds Groq configuration properties |

## Configuration

### application.properties

```properties
# Spring AI Groq Configuration
spring.ai.groq.api-key=your-api-key-here
spring.ai.groq.chat-model=llama-3.3-70b-versatile
spring.ai.groq.base-url=https://api.groq.com/openai
spring.ai.groq.temperature=0.7
spring.ai.groq.max-tokens=2048

# Server Configuration
server.port=8080
spring.application.name=chat-app
```

### Configuration Properties

| Property | Description | Default |
|----------|-------------|---------|
| `spring.ai.groq.api-key` | Groq API key | Required |
| `spring.ai.groq.chat-model` | Model to use | `llama-3.3-70b-versatile` |
| `spring.ai.groq.base-url` | API base URL | `https://api.groq.com/openai` |
| `spring.ai.groq.temperature` | Sampling temperature (0.0-1.0) | `0.7` |
| `spring.ai.groq.max-tokens` | Maximum tokens to generate | `2048` |
| `server.port` | HTTP server port | `8080` |

## API Endpoints

### 1. Send Chat Message

**POST** `/api/chat`

Send a message and receive an AI response.

```bash
curl -s -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"sessionId": "my-session", "message": "Hello, how are you?"}'
```

**Request Body:**
```json
{
  "sessionId": "string (optional, auto-generated if not provided)",
  "message": "string (required)"
}
```

**Response:**
```json
{
  "sessionId": "my-session",
  "response": "Hello! I'm doing well...",
  "history": [
    {"role": "user", "content": "Hello, how are you?"},
    {"role": "assistant", "content": "Hello! I'm doing well..."}
  ]
}
```

### 2. Get Chat History

**GET** `/api/chat/history/{sessionId}`

Get the chat history for a specific session.

```bash
curl -s http://localhost:8080/api/chat/history/my-session
```

**Response:**
```json
[
  {"role": "user", "content": "Hello, how are you?"},
  {"role": "assistant", "content": "Hello! I'm doing well..."}
]
```

### 3. Clear Chat History

**DELETE** `/api/chat/history/{sessionId}`

Clear the chat history for a specific session.

```bash
curl -s -X DELETE http://localhost:8080/api/chat/history/my-session
```

**Response:**
```
Chat history cleared for session: my-session
```

## Building and Running

### Prerequisites

- Java 21 or higher
- Maven (or use the included Maven wrapper)

### Build

```bash
cd chat-app
../mvnw clean package
```

### Run

```bash
../mvnw spring-boot:run
```

Or run the compiled JAR:

```bash
java -jar chat-app/target/chat-app-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## Getting a Groq API Key

1. Visit [Groq Console](https://console.groq.com/)
2. Sign up or log in
3. Navigate to API Keys
4. Create a new API key
5. Update `spring.ai.groq.api-key` in `application.properties`

## Key Design Decisions

### Why this layered architecture?

1. **Controller doesn't use ChatClient directly** - The controller only knows about the service interface, making it:
   - Easier to test (can mock the service)
   - More maintainable (changes to ChatClient don't affect controller)
   - Flexible to swap implementations

2. **ChatClientFactory** - Centralizes ChatClient creation:
   - Reusable across the application
   - Encapsulates memory advisor configuration
   - Easy to add new client configurations

3. **SpringAiChatMemoryAdapter** - Custom memory implementation:
   - In-memory storage (can be swapped for Redis, database, etc.)
   - Session-based isolation using ConcurrentHashMap
   - Implements Spring AI's ChatMemory interface

4. **ConfigurationProperties** - Type-safe configuration:
   - Auto-completion in IDE
   - Validation support
   - Clear documentation of available options

## License

This project is for educational purposes.

