package de.deletelimits.funnymoney.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.service.pojos.TransactionMapping;
import de.deletelimits.funnymoney.ui.main.base.BaseActivity;
import de.deletelimits.funnymoney.ui.main.util.AccountBalancesHelper;
import de.deletelimits.funnymoney.ui.main.util.TransactionListAdapter;

public class TransactionListActivity extends BaseActivity {

    @BindView(R.id.transaction_list_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.header_amount)
    TextView headerAmount;

    @BindView(R.id.total_amount)
    TextView totalBalance;

    @Inject
    PostbankAPI postbankAPI;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_list);
        ButterKnife.bind(this);

        Intent receivedIntent = getIntent();

        headerAmount.setText(receivedIntent.getStringExtra("amount"));

        setSupportActionBar(toolbar);

        totalBalance.setText(AccountBalancesHelper.getInstance().getCurrentBalance(postbankAPI, this));
        List<TransactionMapping> transactionMappings = postbankAPI.getTransactionMappings();
        
        if (getIntent().hasExtra("type")) {
            List<TransactionMapping> finalTransactions = new ArrayList<>();
            for (TransactionMapping transactionMapping : transactionMappings) {
                if (transactionMapping.classification.cost_type.equals(getIntent().getExtras().getString("type"))) {
                    finalTransactions.add(transactionMapping);
                }
            }
            recyclerView.setAdapter(new TransactionListAdapter(this, finalTransactions, this));
        } else {
            recyclerView.setAdapter(new TransactionListAdapter(this, transactionMappings, this));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mock_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
