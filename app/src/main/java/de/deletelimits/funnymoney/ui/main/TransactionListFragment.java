package de.deletelimits.funnymoney.ui.main;


import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.ui.main.base.BaseFragment;
import de.deletelimits.funnymoney.ui.main.util.TransactionListAdapter;

public class TransactionListFragment extends BaseFragment {

    @BindView(R.id.transaction_list_recycler)
    RecyclerView recyclerView;

    @Inject
    PostbankAPI postbankAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_list, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setAdapter(new TransactionListAdapter(postbankAPI.getTransactions()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }

}
