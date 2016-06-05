package de.deletelimits.funnymoney.service.pojos;


import java.util.List;

public class Transaction {
    public String transactionId;
    public List<String> message;
    public String amount;
    public String balance;
    public String currency;
    public List<String> purpose;
    public long bookingDate;
    public long valutaDate;
    public Reference reference;
    public String transactionType;
    public String transactionDetail;
    public List<String> links;
    public boolean future;

}
