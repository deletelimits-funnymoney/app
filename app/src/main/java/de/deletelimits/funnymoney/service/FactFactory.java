package de.deletelimits.funnymoney.service;


import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import de.deletelimits.funnymoney.service.pojos.Fact;
import de.deletelimits.funnymoney.service.pojos.Transaction;
import de.deletelimits.funnymoney.service.pojos.TransactionMapping;

public class FactFactory {

    private PostbankAPI postbankAPI;

    public FactFactory(PostbankAPI postbankAPI)
    {
        this.postbankAPI = postbankAPI;
    }

    public List<Fact> groupTransactionsByGroup(Date start, Date end)
    {
        List <Fact> result = new LinkedList<Fact>();

        Set<String> groups = collectGroups(start, end);

        for (Date date : getDates(start, end)) {
            result.add(aggregateTransactionsForGroups(date, groups));
        }

        return result;
    }

    public Set<String> collectGroups(Date start, Date end) {
        Set<String> result = new HashSet<String>();

        List<TransactionMapping> transactionMappings = getTransactionMappingList(start, end);

        for (TransactionMapping transactionMapping : transactionMappings) {
            result.add(transactionMapping.classification.group);
        }

        return result;
    }

    protected Fact aggregateTransactionsForGroups(Date date, Set<String> groups)
    {
        Fact result = new Fact();

        result.amounts = new HashMap<String, Double>();
        for (String group : groups) {
            result.amounts.put(group, .0);
        }

        List<TransactionMapping> transactionMappings = getTransactionMappingList(date);

        for (TransactionMapping transactionMapping : transactionMappings) {
            Double amount = Double.parseDouble(transactionMapping.transaction.amount);
            String group  = transactionMapping.classification.group;

            Double oldAmount = result.amounts.get(group);
            Double newAmount = oldAmount + amount;

            result.amounts.put(group, newAmount);
        }

        return result;
    }

    public Fact groupSpendingByType(Date start, Date end)
    {
        Set<String> types = collectTypes(start, end);
        Fact result = aggregateSpendingForTypes(start, end, types);

        return result;
    }

    public Set<String> collectTypes(Date start, Date end) {
        Set<String> result = new HashSet<String>();

        List<TransactionMapping> transactionMappings = getTransactionMappingList(start, end);

        for (TransactionMapping transactionMapping : transactionMappings) {
            result.add(transactionMapping.classification.cost_type);
        }

        return result;
    }

    protected Fact aggregateSpendingForTypes(Date date, Set<String> types)
    {
        List<TransactionMapping> transactionMappings = getTransactionMappingList(date);
        return aggregateSpendingForTypes(types, transactionMappings);
    }

    protected Fact aggregateSpendingForTypes(Date start, Date end, Set<String> types)
    {
        List<TransactionMapping> transactionMappings = getTransactionMappingList(start, end);
        return aggregateSpendingForTypes(types, transactionMappings);
    }

    @NonNull
    private Fact aggregateSpendingForTypes(Set<String> types, List<TransactionMapping> transactionMappings) {
        Fact result = new Fact();

        result.amounts = new HashMap<String, Double>();
        for (String type : types) {
            result.amounts.put(type, .0);
        }

        for (TransactionMapping transactionMapping : transactionMappings) {
            Double amount = Double.parseDouble(transactionMapping.transaction.amount);

            // Collect only spendings
            if (amount > 0) {
                continue;
            }

            String type  = transactionMapping.classification.cost_type;

            Double oldAmount = result.amounts.get(type);
            Double newAmount = oldAmount + amount;

            result.amounts.put(type, newAmount);
        }

        return result;
    }

    protected List<Date> getDates(Date start, Date end)
    {
        start = (Date) start.clone();
        end = (Date) end.clone();

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        endCalendar.add(Calendar.SECOND, 2);

        List<Date> result = new LinkedList<Date>();

        for (Date date = startCalendar.getTime(); startCalendar.before(endCalendar); startCalendar.add(Calendar.DATE, 1), date = startCalendar.getTime()) {
            result.add(date);
        }

        return result;
    }

    protected List<TransactionMapping> getTransactionMappingList(Date date)
    {
        return getTransactionMappingList(date, date);
    }

    protected List<TransactionMapping> getTransactionMappingList(Date start, Date end)
    {
        start = (Date) start.clone();
        end = (Date) end.clone();

        start.setHours(0);
        start.setMinutes(0);
        start.setSeconds(0);

        end.setHours(23);
        end.setMinutes(59);
        end.setSeconds(59);

        Long startTimestamp = start.getTime();
        Long endTimestamp = end.getTime();

        List<TransactionMapping> result = new LinkedList<TransactionMapping>();
        List<TransactionMapping> transactionsMappings = postbankAPI.getTransactionMappings();

        for (TransactionMapping transactionsMapping : transactionsMappings) {
            if (startTimestamp <= transactionsMapping.transaction.bookingDate
                    && transactionsMapping.transaction.bookingDate <= endTimestamp) {
                result.add(transactionsMapping);
            }
        }

        return result;
    }

}
