package de.deletelimits.funnymoney.service;

import java.util.List;

import de.deletelimits.funnymoney.service.pojos.Transaction;

public interface PostbankAPI {

    List<Transaction> getTransactions();
}
