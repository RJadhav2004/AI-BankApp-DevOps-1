package com.example.bankapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a bank account entity.
 */
@Entity
@Table(name = "accounts")
public final class Account implements UserDetails {

    /**
     * Precision for balance column.
     */
    private static final int BALANCE_PRECISION = 19;

    /**
     * Account identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Username.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * Password.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Account balance.
     */
    @Column(
            nullable = false,
            precision = BALANCE_PRECISION,
            scale = 2
    )
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * Transactions associated with the account.
     */
    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Transaction> transactions = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Account() {
    }

    /**
     * Parameterized constructor.
     *
     * @param accountUsername account username
     * @param accountPassword account password
     */
    public Account(
            final String accountUsername,
            final String accountPassword) {

        this.username = accountUsername;
        this.password = accountPassword;
        this.balance = BigDecimal.ZERO;
    }

    /**
     * Returns account id.
     *
     * @return account id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets account id.
     *
     * @param accountId account id
     */
    public void setId(final Long accountId) {
        this.id = accountId;
    }

    /**
     * Returns username.
     *
     * @return username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets username.
     *
     * @param accountUsername username
     */
    public void setUsername(final String accountUsername) {
        this.username = accountUsername;
    }

    /**
     * Returns password.
     *
     * @return password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets password.
     *
     * @param accountPassword password
     */
    public void setPassword(final String accountPassword) {
        this.password = accountPassword;
    }

    /**
     * Returns account balance.
     *
     * @return account balance
     */
    public BigDecimal getBalance() {
        return this.balance;
    }

    /**
     * Sets account balance.
     *
     * @param accountBalance account balance
     */
    public void setBalance(final BigDecimal accountBalance) {
        this.balance = accountBalance;
    }

    /**
     * Returns transactions.
     *
     * @return list of transactions
     */
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    /**
     * Sets transactions.
     *
     * @param transactionList transaction list
     */
    public void setTransactions(
            final List<Transaction> transactionList) {

        this.transactions = transactionList;
    }

    /**
     * Returns granted authorities.
     *
     * @return authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Indicates whether account is non-expired.
     *
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether account is non-locked.
     *
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether credentials are non-expired.
     *
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether account is enabled.
     *
     * @return true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
