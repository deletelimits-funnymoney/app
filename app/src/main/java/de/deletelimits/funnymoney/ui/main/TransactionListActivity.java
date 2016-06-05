package de.deletelimits.funnymoney.ui.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.ui.main.base.BaseActivity;
import de.deletelimits.funnymoney.ui.main.util.AccountBalancesHelper;
import de.deletelimits.funnymoney.ui.main.util.TransactionListAdapter;

public class TransactionListActivity extends BaseActivity {

    @BindView(R.id.transaction_list_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.available_amount)
    TextView availableAmount;

    @BindView(R.id.total_amount)
    TextView totalBalance;

    @Inject
    PostbankAPI postbankAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_list);
        ButterKnife.bind(this);

        totalBalance.setText(AccountBalancesHelper.getInstance().getCurrentBalance(postbankAPI, this));

        recyclerView.setAdapter(new TransactionListAdapter(this, postbankAPI.getTransactionMappings(), this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }



}
