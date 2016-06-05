package de.deletelimits.funnymoney.service;


import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.app.Application;
import de.deletelimits.funnymoney.service.pojos.Transaction;
import de.deletelimits.funnymoney.service.pojos.TransactionMapping;
import de.deletelimits.funnymoney.service.util.JSONResourceReader;

public class PostbankAPIMockImpl implements PostbankAPI {

    private final Application application;

    public PostbankAPIMockImpl(Application application) {
        this.application = application;
    }

    @Override
    public List<Transaction> getTransactions() {
        JSONResourceReader reader = new JSONResourceReader(application.getResources(), R.raw.transactions);
        Type type = new TypeToken<List<Transaction>>() {
        }.getType();
        List<Transaction> transactions = reader.constructUsingGson(type);
        Collections.sort(transactions, (lhs, rhs) -> new Long(rhs.bookingDate).compareTo(lhs.bookingDate));
        return transactions;
    }


    @Override
    public List<TransactionMapping> getTransactionMappings() {
        JSONResourceReader reader = new JSONResourceReader(application.getResources(), R.raw.transaction_classification);
        Type type = new TypeToken<List<TransactionMapping>>() {
        }.getType();
        List<Transaction> transactions = this.getTransactions();

        List<TransactionMapping> transactionMappings = reader.constructUsingGson(type);
        for (Transaction transaction : transactions) {
            for (TransactionMapping transactionMapping : transactionMappings) {
                if (transaction.transactionId.equals(transactionMapping.transactionId)) {
                    transactionMapping.transaction = transaction;
                }
            }
        }
        Collections.sort(transactionMappings, (lhs, rhs) -> new Long(rhs.transaction.bookingDate).compareTo(lhs.transaction.bookingDate));
        return transactionMappings;
    }
}
