package de.deletelimits.funnymoney.ui.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.ui.main.base.BaseActivity;
import de.deletelimits.funnymoney.ui.main.util.TransactionListAdapter;

public class TransactionListActivity extends BaseActivity {

    @BindView(R.id.transaction_list_recycler)
    RecyclerView recyclerView;

    @Inject
    PostbankAPI postbankAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_list);
        ButterKnife.bind(this);
        recyclerView.setAdapter(new TransactionListAdapter(postbankAPI.getTransactionMappings(), this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
