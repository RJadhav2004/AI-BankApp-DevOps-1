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
     * Balance precision.
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
     * Current account balance.
     */
    @Column(nullable = false,
            precision = BALANCE_PRECISION,
            scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * Account transactions.
     */
    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    /**
     * Default constructor.
     */
    public Account() {
    }

    /**
     * Creates an account.
     *
     * @param username account username
     * @param password account password
     */
    public Account(final String username,
                   final String password) {
        this.username = username;
        this.password = password;
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
     * @param id account id
     */
    public void setId(final Long id) {
        this.id = id;
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
     * @param username username
     */
    public void setUsername(final String username) {
        this.username = username;
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
     * @param password password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Returns account balance.
     *
     * @return balance
     */
    public BigDecimal getBalance() {
        return this.balance;
    }

    /**
     * Sets account balance.
     *
     * @param balance account balance
     */
    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Returns transactions.
     *
     * @return transaction list
     */
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    /**
     * Sets transactions.
     *
     * @param transactions transaction list
     */
    public void setTransactions(
            final List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Returns authorities.
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
