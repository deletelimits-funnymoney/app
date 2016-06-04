package de.deletelimits.funnymoney.ui.main;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.service.pojos.Transaction;
import de.deletelimits.funnymoney.ui.main.base.BaseActivity;
import de.deletelimits.funnymoney.ui.main.util.AccountBalancesHelper;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Inject
    PostbankAPI postbankAPI;

    @BindView(R.id.card_view)
    CardView totalCard;

    @BindView(R.id.available_amount)
    TextView availableAmount;

    @BindView(R.id.total_amount)
    TextView currentBalance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);
        initOverviewData();
    }

    private void initOverviewData() {
        currentBalance.setText(AccountBalancesHelper.getInstance().getCurrentBalance(postbankAPI, this));
    }



    public void goToTransactions(View v){
        Intent intent = new Intent(this, TransactionListActivity.class);

        Pair p1 = new Pair<>(availableAmount, "main_amount");
        Pair p2 = new Pair<>(currentBalance, "total_amount");
        Pair p3 = new Pair<>(totalCard, "total_card");


        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, p1, p2,p3);
        startActivity(intent, options.toBundle());
    }
//    @OnClick(R.id.main_layout_button)
//    void onClick() {
//        Toast.makeText(this, "click!!", Toast.LENGTH_SHORT).show();
//    }

}
