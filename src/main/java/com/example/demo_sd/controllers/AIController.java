package com.example.demo_sd.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo_sd.services.ChatClient;
import java.util.Map;
@RestController
class AIController {
	private final ChatClient chatClient;

	AIController(ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	@GetMapping("/ai")
	public Map<String, String> completion(@RequestParam(value = "message", defaultValue = "Can you provide an overview of the recent trends in the American stock market?") String message) {
		ChatClient.OpenAIResponse response = chatClient.sendMessage(message);
		String content = response.getChoices().get(0).getMessage().getContent();

		// Supponiamo che il contenuto sia strutturato come "Titolo: Descrizione"
		String[] parts = content.split(": ", 2);
		String title = parts.length > 0 ? parts[0] : "No Title";
		String description = parts.length > 1 ? parts[1] : "No Description";

		return Map.of("title", title, "description", description);
	}
}

