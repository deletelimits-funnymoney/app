package de.deletelimits.funnymoney.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.PostbankAPI;
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

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initOverviewData();
    }

    private void initOverviewData() {
        currentBalance.setText(AccountBalancesHelper.getInstance().getCurrentBalance(postbankAPI, this));
    }

    public void goToTransactions(View v) {
        Intent intent = new Intent(this, TransactionListActivity.class);

        Pair p1 = new Pair<>(availableAmount, "main_amount");
        Pair p2 = new Pair<>(currentBalance, "total_amount");
        Pair p3 = new Pair<>(totalCard, "total_card");


        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, p1, p2, p3);
        startActivity(intent, options.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mock_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
