package de.deletelimits.funnymoney.service;


import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
        return reader.constructUsingGson(type);
    }


    @Override
    public List<TransactionMapping> getTransactionMappings() {
        JSONResourceReader reader = new JSONResourceReader(application.getResources(), R.raw.transaction_classification);
        Type type = new TypeToken<List<TransactionMapping>>() {
        }.getType();
        List<Transaction> transactions = this.getTransactions();
        List<TransactionMapping> transactionMappings = reader.constructUsingGson(type);
        for (TransactionMapping transactionMapping : transactionMappings) {
            for (Transaction transaction : transactions) {
                if (transaction.transactionId.equals(transactionMapping.transactionId)) {
                    transactionMapping.transaction = transaction;
                }
            }
        }
        return transactionMappings;
    }
}
