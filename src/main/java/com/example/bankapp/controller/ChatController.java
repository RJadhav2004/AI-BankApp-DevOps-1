package com.example.bankapp.controller;

import com.example.bankapp.model.Account;
import com.example.bankapp.service.ChatService;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for chat operations.
 */
@RestController
@RequestMapping("/api")
public final class ChatController {

    /**
     * Service for handling chat requests.
     */
    private final ChatService chatService;

    /**
     * Creates a new chat controller.
     *
     * @param service chat service
     */
    public ChatController(final ChatService service) {
        this.chatService = service;
    }

    /**
     * Processes a chat request from the authenticated user.
     *
     * @param account authenticated account
     * @param request request body containing the message
     * @return chat response
     */
    @PostMapping("/chat")
    public Map<String, String> chat(
            @AuthenticationPrincipal final Account account,
            @RequestBody final Map<String, String> request) {

        final String message = request.getOrDefault("message", "");
        final String reply = chatService.chat(account, message);
        return Map.of("reply", reply);
    }
}
