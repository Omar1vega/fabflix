package edu.uci.ics.vegao1.service.billing.records.sales;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.paypal.api.payments.Currency;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Transaction {
    private String transactionId;
    private String state;
    private GhettoAmount amount;
    private Currency transaction_fee;
    private String create_time;
    private String update_time;

    Transaction(String transactionId, String state, GhettoAmount amount, Currency transaction_fee, String create_time, String update_time) {
        this.transactionId = transactionId;
        this.state = state;
        this.amount = amount;
        this.transaction_fee = transaction_fee;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getTransactionId() {
        return transactionId;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getState() {
        return state;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setState(String state) {
        this.state = state;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public GhettoAmount getAmount() {
        return amount;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setAmount(GhettoAmount amount) {
        this.amount = amount;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public Currency getTransaction_fee() {
        return transaction_fee;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setTransaction_fee(Currency transaction_fee) {
        this.transaction_fee = transaction_fee;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getCreate_time() {
        return create_time;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getUpdate_time() {
        return update_time;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
