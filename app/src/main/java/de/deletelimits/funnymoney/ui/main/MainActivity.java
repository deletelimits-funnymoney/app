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

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.FactFactory;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.service.pojos.Fact;
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

    @BindView(R.id.fix_amount)
    TextView fixedAmount;

    @BindView(R.id.variable_amount)
    TextView variableAmount;

    private int availableAmountValue;
    private int fixedAmountValue;
    private int variableAmountValue;

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
        int currentBalanceValue = (int) AccountBalancesHelper.getInstance().getCurrentBalanceValue(postbankAPI);
        currentBalance.setText(AccountBalancesHelper.getInstance().getCurrentBalance(postbankAPI, this));

        //Set fixed and variable amounts
        FactFactory x = new FactFactory(postbankAPI);
        Calendar start = Calendar.getInstance();
        start.set(2016, 3, 28, 0, 0, 0);
        Calendar end = Calendar.getInstance();
        end.set(2016, 4, 31, 0, 0, 0);

        Fact f = x.groupSpendingByType(start.getTime(), end.getTime());
        variableAmountValue = (int) (f.amounts.get("variable") * -1);
        variableAmount.setText(variableAmountValue + "€");
        fixedAmountValue = (int) (f.amounts.get("fixed") * -1);
        fixedAmount.setText(fixedAmountValue + "€");

        availableAmountValue = currentBalanceValue - fixedAmountValue;
        availableAmount.setText(availableAmountValue + "€");
    }

    @OnClick(R.id.detail_button_overall)
    public void goToTransactions(View v) {
        Intent intent = new Intent(this, TransactionListActivity.class);

        Pair p1;
        Pair p2 = new Pair<>(currentBalance, "total_amount");
        Pair p3 = new Pair<>(totalCard, "total_card");

        String bundleAmount;

        switch (v.getId()){
            case R.id.detail_button_overall:
                bundleAmount = availableAmountValue + "€";
                availableAmount.setTransitionName("main_amount");
                p1 = new Pair<>(availableAmount, "main_amount");
                break;
            case R.id.detail_button_fix:
                bundleAmount = fixedAmountValue + "€";
                intent.putExtra("type", "fixed");
                fixedAmount.setTransitionName("main_amount");
                p1 = new Pair<>(fixedAmount, "main_amount");
                break;
            case R.id.detail_button_variable:
                bundleAmount = variableAmountValue + "€";
                variableAmount.setTransitionName("main_amount");
                p1 = new Pair<>(variableAmount, "main_amount");
                intent.putExtra("type", "variable");
                break;
            default:
                bundleAmount = "0€";
                availableAmount.setTransitionName("header_amount");
                p1 = new Pair<>(availableAmount, "header_amount");
        }
        intent.putExtra("amount",bundleAmount);

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
