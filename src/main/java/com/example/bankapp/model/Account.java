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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents a bank account.
 */
@Entity
@Table(name = "accounts")
public final class Account implements UserDetails, Serializable {

    /**
     * Serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Balance precision.
     */
    private static final int BALANCE_PRECISION = 19;

    /**
     * Default balance scale.
     */
    private static final int BALANCE_SCALE = 2;

    /**
     * User role.
     */
    private static final String ROLE_USER = "ROLE_USER";

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
            scale = BALANCE_SCALE
    )
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * Account transactions.
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
     * Creates an account.
     *
     * @param accountUsername username
     * @param accountPassword password
     */
    public Account(
            final String accountUsername,
            final String accountPassword) {

        this.username = accountUsername;
        this.password = accountPassword;
        this.balance = BigDecimal.ZERO;
    }

        /**
     * Returns the account identifier.
     *
     * @return account identifier
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the account identifier.
     *
     * @param accountId account identifier
     */
    public void setId(final Long accountId) {
        this.id = accountId;
    }

    /**
     * Returns the username.
     *
     * @return username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the username.
     *
     * @param accountUsername username
     */
    public void setUsername(final String accountUsername) {
        this.username = accountUsername;
    }

    /**
     * Returns the password.
     *
     * @return password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password.
     *
     * @param accountPassword password
     */
    public void setPassword(final String accountPassword) {
        this.password = accountPassword;
    }

    /**
     * Returns the account balance.
     *
     * @return account balance
     */
    public BigDecimal getBalance() {
        return this.balance;
    }

    /**
     * Sets the account balance.
     *
     * @param accountBalance account balance
     */
    public void setBalance(final BigDecimal accountBalance) {
        this.balance = accountBalance;
    }

    /**
     * Returns the transaction list.
     *
     * @return unmodifiable transaction list
     */
    public List<Transaction> getTransactions() {
        if (this.transactions == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(this.transactions);
    }

    /**
     * Sets the transaction list.
     *
     * @param transactionList transaction list
     */
    public void setTransactions(
            final List<Transaction> transactionList) {

        if (transactionList == null) {
            this.transactions = new ArrayList<>();
        } else {
            this.transactions = new ArrayList<>(transactionList);
        }
    }

    /**
     * Returns granted authorities.
     *
     * @return granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ROLE_USER));
    }

        /**
     * Indicates whether the account is non-expired.
     *
     * @return true if the account is non-expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the account is non-locked.
     *
     * @return true if the account is non-locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the credentials are non-expired.
     *
     * @return true if the credentials are non-expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the account is enabled.
     *
     * @return true if the account is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
