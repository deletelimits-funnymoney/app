package de.deletelimits.funnymoney.service.pojos;


import java.util.List;

public class Transaction {
    String transactionId;
    List<String> message;
    String amount;
    String balance;
    String currency;
    List<String> purpose;
    long bookingDate;
    long valutaDate;
    Reference reference;
    String transactionType;
    String transactionDetail;
    List<String> links;
}
