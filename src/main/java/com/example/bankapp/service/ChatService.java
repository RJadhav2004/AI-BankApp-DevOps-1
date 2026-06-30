package com.example.bankapp.service;

import com.example.bankapp.model.Account;
import com.example.bankapp.model.Transaction;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service responsible for interacting with the Ollama AI model
 * to answer banking-related questions.
 */
@Service
public final class ChatService {

    /**
     * Maximum number of recent transactions included in context.
     */
    private static final int MAX_TRANSACTIONS = 5;

    /**
     * Ollama server URL.
     */
    @Value("${ollama.url}")
    private String ollamaUrl;

    /**
     * Ollama model name.
     */
    @Value("${ollama.model}")
    private String model;

    /**
     * REST client used to communicate with Ollama.
     */
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Account service for retrieving account information.
     */
    private final AccountService accountService;

    /**
     * Constructs a new ChatService.
     *
     * @param service account service
     */
    public ChatService(final AccountService service) {
        this.accountService = service;
    }

    /**
     * Sends a user message to the AI assistant.
     *
     * @param account user account
     * @param userMessage message from the user
     * @return AI response
     */
    @SuppressWarnings("unchecked")
    public String chat(
            final Account account,
            final String userMessage) {

        List<Transaction> recent =
                accountService.getTransactionHistory(account);

        String context =
                buildContext(account, recent);

        Map<String, Object> request =
                Map.of(
                        "model",
                        model,
                        "messages",
                        List.of(
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
                        "stream",
                        false
                );

        try {

            Map<String, Object> response =
                    restTemplate.postForObject(
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

            return "Sorry, I couldn't process that.";

        } catch (Exception exception) {

            return "AI assistant is unavailable. "
                    + "Please make sure Ollama is running.";
        }
    }

    /**
     * Builds AI context using account information and transactions.
     *
     * @param account account details
     * @param transactions recent transactions
     * @return context string
     */
    private String buildContext(
            final Account account,
            final List<Transaction> transactions) {

        StringBuilder sb = new StringBuilder();

        sb.append("You are a helpful banking assistant for BankApp. ");
        sb.append(
                "Keep answers short and friendly "
                        + "(2-3 sentences max)."
        );

        sb.append("\n\nCustomer details:");
        sb.append("\n- Username: ")
                .append(account.getUsername());

        sb.append("\n- Balance: $")
                .append(account.getBalance());

        sb.append("\n- Account ID: ")
                .append(account.getId());

        if (!transactions.isEmpty()) {

            sb.append("\n\nRecent transactions:");

            int limit =
                    Math.min(
                            transactions.size(),
                            MAX_TRANSACTIONS
                    );

            for (int i = 0; i < limit; i++) {

                Transaction transaction =
                        transactions.get(i);

                sb.append("\n- ")
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

            sb.append("\n\nNo transactions yet.");
        }

        return sb.toString();
    }
}
