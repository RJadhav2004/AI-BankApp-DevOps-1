package com.example.bankapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a banking transaction.
 */
@Entity
@Table(name = "transactions")
public final class Transaction {

    /**
     * Precision value for amount column.
     */
    private static final int AMOUNT_PRECISION = 19;

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
        scale = 2
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
     * Associated account.
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
     * @param txnAmount transaction amount
     * @param txnType transaction type
     * @param txnTimestamp transaction timestamp
     * @param txnAccount associated account
     */
    public Transaction(
            final BigDecimal txnAmount,
            final String txnType,
            final LocalDateTime txnTimestamp,
            final Account txnAccount) {

        this.amount = txnAmount;
        this.type = txnType;
        this.timestamp = txnTimestamp;
        this.account = txnAccount;
    }

    /**
     * Returns transaction id.
     *
     * @return transaction id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets transaction id.
     *
     * @param transactionId transaction id
     */
    public void setId(final Long transactionId) {
        this.id = transactionId;
    }

    /**
     * Returns transaction amount.
     *
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets transaction amount.
     *
     * @param txnAmount transaction amount
     */
    public void setAmount(final BigDecimal txnAmount) {
        this.amount = txnAmount;
    }

    /**
     * Returns transaction type.
     *
     * @return transaction type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets transaction type.
     *
     * @param txnType transaction type
     */
    public void setType(final String txnType) {
        this.type = txnType;
    }

    /**
     * Returns transaction timestamp.
     *
     * @return timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets transaction timestamp.
     *
     * @param txnTimestamp transaction timestamp
     */
    public void setTimestamp(final LocalDateTime txnTimestamp) {
        this.timestamp = txnTimestamp;
    }

    /**
     * Returns associated account.
     *
     * @return account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets associated account.
     *
     * @param txnAccount account
     */
    public void setAccount(final Account txnAccount) {
        this.account = txnAccount;
    }
}
