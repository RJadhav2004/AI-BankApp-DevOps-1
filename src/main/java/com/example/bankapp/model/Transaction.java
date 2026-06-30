package com.example.bankapp.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a banking transaction.
 */
@Entity
@Table(name = "transactions")
@SuppressFBWarnings(
        value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"},
        justification = "JPA entity relationship")
public final class Transaction implements Serializable {

    /**
     * Serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Precision for amount.
     */
    private static final int AMOUNT_PRECISION = 19;

    /**
     * Scale for amount.
     */
    private static final int AMOUNT_SCALE = 2;

    /**
     * Transaction identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Transaction amount.
     */
    @Column(
            nullable = false,
            precision = AMOUNT_PRECISION,
            scale = AMOUNT_SCALE
    )
    private BigDecimal amount;

    /**
     * Transaction type.
     */
    @Column(nullable = false)
    private String type;

    /**
     * Transaction timestamp.
     */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /**
     * Related account.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    /**
     * Default constructor.
     */
    public Transaction() {
    }

    /**
     * Creates a transaction.
     *
     * @param transactionAmount amount
     * @param transactionType type
     * @param transactionTimestamp timestamp
     * @param transactionAccount account
     */
    public Transaction(
            final BigDecimal transactionAmount,
            final String transactionType,
            final LocalDateTime transactionTimestamp,
            final Account transactionAccount) {

        this.amount = transactionAmount;
        this.type = transactionType;
        this.timestamp = transactionTimestamp;
        this.account = transactionAccount;
    }

        /**
     * Returns the transaction identifier.
     *
     * @return transaction identifier
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Sets the transaction identifier.
     *
     * @param transactionId transaction identifier
     */
    public void setId(final Long transactionId) {
        this.id = transactionId;
    }

    /**
     * Returns the transaction amount.
     *
     * @return transaction amount
     */
    public BigDecimal getAmount() {
        return this.amount;
    }

    /**
     * Sets the transaction amount.
     *
     * @param transactionAmount transaction amount
     */
    public void setAmount(final BigDecimal transactionAmount) {
        this.amount = transactionAmount;
    }

    /**
     * Returns the transaction type.
     *
     * @return transaction type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets the transaction type.
     *
     * @param transactionType transaction type
     */
    public void setType(final String transactionType) {
        this.type = transactionType;
    }

    /**
     * Returns the transaction timestamp.
     *
     * @return transaction timestamp
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Sets the transaction timestamp.
     *
     * @param transactionTimestamp transaction timestamp
     */
    public void setTimestamp(
            final LocalDateTime transactionTimestamp) {
        this.timestamp = transactionTimestamp;
    }

    /**
     * Returns the associated account.
     *
     * @return account
     */
    public Account getAccount() {
        return this.account;
    }

    /**
     * Sets the associated account.
     *
     * @param transactionAccount account
     */
    public void setAccount(final Account transactionAccount) {
        this.account = transactionAccount;
    }
    }
