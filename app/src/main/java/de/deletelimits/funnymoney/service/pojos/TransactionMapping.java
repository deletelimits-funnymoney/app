package de.deletelimits.funnymoney.service.pojos;


import com.google.gson.Gson;

public class TransactionMapping {
    public String transactionId;
    public TransactionClassification classification;
    public Transaction transaction;

    // You can add those functions as LiveTemplate !
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static TransactionMapping fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, TransactionMapping.class);
    }
}
