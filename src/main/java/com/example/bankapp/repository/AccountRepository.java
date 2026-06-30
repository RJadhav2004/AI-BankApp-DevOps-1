package com.example.bankapp.repository;

import com.example.bankapp.model.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Account entity.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Finds an account by username.
     *
     * @param username the username to search for
     * @return an Optional containing the matching account if found,
     *         otherwise an empty Optional
     */
    Optional<Account> findByUsername(String username);
}
