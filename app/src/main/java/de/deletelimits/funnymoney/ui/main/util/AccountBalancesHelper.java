package de.deletelimits.funnymoney.ui.main.util;

import android.app.Activity;
import android.content.res.Resources;

import java.util.List;

import javax.inject.Inject;

import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.service.pojos.Transaction;

/**
 * Created by Farbklex on 04.06.2016.
 */
public class AccountBalancesHelper {

    private static AccountBalancesHelper mAccountBalancesHelper;

    //Private constructor
    private AccountBalancesHelper(){};

    public static AccountBalancesHelper getInstance(){
        if(mAccountBalancesHelper == null){
            mAccountBalancesHelper = new AccountBalancesHelper();
        }
        return mAccountBalancesHelper;
    }

    /**
     * Extracts the current real balance from the latest transaction
     *
     * @param postbankAPI
     * @param activity
     * @return
     */
    public String getCurrentBalance(PostbankAPI postbankAPI, Activity activity) {
        List<Transaction> transactions = postbankAPI.getTransactions();
        Transaction latestTransaction = transactions.get(transactions.size()-1);

        Resources res = activity.getResources();
        String currentBalance = String.format(res.getString(R.string.current_balance), latestTransaction.balance);
        return currentBalance;
    }
}
