package com.example.demo_sd.services;

import org.springframework.ai.chat.metadata.Usage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class ChatClient {

    private final RestTemplate restTemplate;

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public ChatClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OpenAIResponse call(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        String body = String.format("{\"model\": \"gpt-4o-mini\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
                prompt);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<OpenAIResponse> responseEntity = restTemplate.exchange(API_URL, HttpMethod.POST, entity, OpenAIResponse.class);
        return responseEntity.getBody();
    }

    public OpenAIResponse sendMessage(String userMessage) {

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        String body = "{\n" +
                "    \"model\": \"gpt-4o-mini\",\n" +
                "    \"messages\": [\n" +
                "      {\n" +
                "        \"role\": \"system\",\n" +
                "        \"content\": \"You are a helpful assistant.\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"role\": \"user\",\n" +
                "        \"content\": \"" + userMessage + "\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }";

        // Creiamo l'entity che contiene headers e body
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        // Effettuiamo la richiesta POST
        ResponseEntity<OpenAIResponse> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, OpenAIResponse.class);

        // Restituiamo la risposta dal server OpenAI
        return response.getBody();
    }
    public static class OpenAIResponse {
        private String id;
        private String object;
        private long created;
        private String model;
        private List<Choice> choices;
        private Usage usage;


        public List<Choice> getChoices() {
            return choices;
        }

        public static class Choice {
            private Message message;

            public Message getMessage() {
                return message;
            }
        }

        public static class Message {
            private String content;

            public String getContent() {
                return content;
            }
        }
    }
}


