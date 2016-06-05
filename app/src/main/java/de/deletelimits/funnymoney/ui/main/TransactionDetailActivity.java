package de.deletelimits.funnymoney.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.service.pojos.Transaction;
import de.deletelimits.funnymoney.service.pojos.TransactionMapping;
import de.deletelimits.funnymoney.ui.main.base.BaseActivity;
import de.deletelimits.funnymoney.ui.main.util.AccountBalancesHelper;

/**
 * Created by Farbklex on 04.06.2016.
 * Detail of a transaction
 */
public class TransactionDetailActivity extends BaseActivity {

    @Inject
    PostbankAPI postbankAPI;

    @BindView(R.id.transaction_amount)
    TextView transactionAmount;

    @BindView(R.id.transaction_reason)
    TextView transactionReason;

    @BindView(R.id.transaction_reference_name)
    TextView transacionReferenceName;

    @BindView(R.id.transaction_date)
    TextView transactionDate;

    @BindView(R.id.card_view)
    View cardView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int greenColor;
    private int redColor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        this.greenColor = ContextCompat.getColor(this, R.color.primary);
        this.redColor = ContextCompat.getColor(this, R.color.red);

        Intent receivedIntent = getIntent();

        TransactionMapping transactionMapping = TransactionMapping.fromJson(receivedIntent.getStringExtra("transaction"));
        Transaction transaction = transactionMapping.transaction;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transactionAmount.setTransitionName(receivedIntent.getStringExtra("amount_transition_name"));
            transacionReferenceName.setTransitionName(receivedIntent.getStringExtra("referenceName_transition_name"));
        }

        transactionAmount.setText(transaction.amount + " €");
        transactionAmount.setTextColor((transaction.amount.charAt(0) == '-') ? redColor : greenColor);
        transacionReferenceName.setText(transaction.reference.accountHolder);
        transactionReason.setText(TextUtils.join(" ", transactionMapping.transaction.purpose.toArray()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMAN);
        transactionDate.setText(sdf.format(transaction.bookingDate));
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
