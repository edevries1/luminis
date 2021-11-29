package eu.luminis.model;

import java.math.BigDecimal;


public class MT940Record {
    private String transactionReference;
    private String accountNumber;
    private BigDecimal startingBalance;
    private BigDecimal mutation;
    private String description;
    private BigDecimal endBalance;

    public MT940Record() {

    }

    public MT940Record(
            String transactionReference,
            String accountNumber,
            BigDecimal startingBalance,
            BigDecimal mutation,
            String description,
            BigDecimal endBalance
    ) {
        this.transactionReference = transactionReference;
        this.accountNumber = accountNumber;
        this.startingBalance = startingBalance;
        this.mutation = mutation;
        this.description = description;
        this.endBalance = endBalance;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getStartingBalance() {
        return startingBalance;
    }

    public void setStartingBalance(BigDecimal startingBalance) {
        this.startingBalance = startingBalance;
    }

    public BigDecimal getMutation() {
        return mutation;
    }

    public void setMutation(BigDecimal mutation) {
        this.mutation = mutation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(BigDecimal endBalance) {
        this.endBalance = endBalance;
    }
}
