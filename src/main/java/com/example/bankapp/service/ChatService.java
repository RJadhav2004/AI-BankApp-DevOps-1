package com.example.bankapp.service;

import com.example.bankapp.model.Account;
import com.example.bankapp.model.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Service for interacting with the AI chat assistant.
 */
@Service
public final class ChatService {

    /**
     * Maximum number of transactions to include.
     */
    private static final int MAX_TRANSACTIONS = 5;

    /**
     * Ollama base URL.
     */
    @Value("${ollama.url}")
    private String ollamaUrl;

    /**
     * Ollama model.
     */
    @Value("${ollama.model}")
    private String model;

    /**
     * REST client.
     */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Account service.
     */
    private final AccountService accountService;

    /**
     * Creates a chat service.
     *
     * @param service account service
     */
    public ChatService(final AccountService service) {
        this.accountService = service;
    }

    /**
     * Sends a message to the AI assistant.
     *
     * @param account user account
     * @param userMessage user message
     * @return AI response
     */
    @SuppressWarnings("unchecked")
    public String chat(
            final Account account,
            final String userMessage) {

        List<Transaction> recent =
                accountService.getTransactionHistory(account);

        String context = buildContext(account, recent);

        Map<String, Object> request = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of(
                                "role",
                                "system",
                                "content",
                                context
                        ),
                        Map.of(
                                "role",
                                "user",
                                "content",
                                userMessage
                        )
                ),
                "stream", false
        );
                try {
            Map<String, Object> response = restTemplate.postForObject(
                    ollamaUrl + "/api/chat",
                    request,
                    Map.class
            );

            if (response != null
                    && response.containsKey("message")) {

                Map<String, String> message =
                        (Map<String, String>) response.get("message");

                return message.get("content");
            }

            return "Sorry, I couldn't process your request.";

        } catch (RestClientException exception) {

            return "AI assistant is unavailable. "
                    + "Please make sure Ollama is running.";
        }
    }

    /**
     * Builds the AI context.
     *
     * @param account account
     * @param transactions recent transactions
     * @return context string
     */
    private String buildContext(
            final Account account,
            final List<Transaction> transactions) {

        StringBuilder builder = new StringBuilder();

        builder.append(
                "You are a helpful banking assistant for BankApp. ");
        builder.append(
                "Keep answers short and friendly ");
        builder.append("(2-3 sentences max).");

        builder.append("\n\nCustomer details:");
        builder.append("\n- Username: ")
                .append(account.getUsername());

        builder.append("\n- Balance: $")
                .append(account.getBalance());

        builder.append("\n- Account ID: ")
                .append(account.getId());

        if (!transactions.isEmpty()) {

            builder.append("\n\nRecent transactions:");

            int limit =
                    Math.min(
                            transactions.size(),
                            MAX_TRANSACTIONS
                    );

            for (int index = 0; index < limit; index++) {

                Transaction transaction =
                        transactions.get(index);

                        builder.append("\n- ")
                        .append(transaction.getType())
                        .append(": $")
                        .append(transaction.getAmount())
                        .append(" on ")
                        .append(
                                transaction.getTimestamp()
                                        .toLocalDate()
                        );
            }

        } else {
            builder.append("\n\nNo transactions yet.");
        }

        return builder.toString();
    }
}
