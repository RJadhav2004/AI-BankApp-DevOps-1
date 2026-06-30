package com.example.bankapp.repository;

import com.example.bankapp.model.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Transaction entities.
 */
public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    /**
     * Finds all transactions for the given account ordered by timestamp
     * in descending order.
     *
     * @param accountId the account identifier
     * @return list of transactions ordered by newest first
     */
    List<Transaction> findByAccountIdOrderByTimestampDesc(
            Long accountId);
}
