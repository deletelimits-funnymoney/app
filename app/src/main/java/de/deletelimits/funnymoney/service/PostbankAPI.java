package de.deletelimits.funnymoney.service;

import java.util.List;

import de.deletelimits.funnymoney.service.pojos.Transaction;
import de.deletelimits.funnymoney.service.pojos.TransactionClassification;
import de.deletelimits.funnymoney.service.pojos.TransactionMapping;

public interface PostbankAPI {

    List<Transaction> getTransactions();

    List<TransactionMapping> getTransactionMappings();
}
