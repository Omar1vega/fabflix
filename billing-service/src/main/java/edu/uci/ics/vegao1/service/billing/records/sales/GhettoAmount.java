package edu.uci.ics.vegao1.service.billing.records.sales;

public class GhettoAmount {
    private String total;
    private String currency;

    GhettoAmount(String total, String currency) {
        this.total = total;
        this.currency = currency;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getTotal() {
        return total;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setTotal(String total) {
        this.total = total;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public String getCurrency() {
        return currency;
    }

    @SuppressWarnings("unused") //Used by Jackson
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
