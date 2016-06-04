package de.deletelimits.funnymoney.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.ui.main.base.BaseActivity;


public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Inject
    PostbankAPI postbankAPI;

    @BindView(R.id.card_view)
    CardView totalCard;

    @BindView(R.id.available_amount)
    TextView availableAmount;

    @BindView(R.id.total_amount)
    TextView totalAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ButterKnife.bind(this);


    }

    public void goToTransactions(View v){
        Intent intent = new Intent(this, TransactionListActivity.class);

        Pair p1 = new Pair<>(availableAmount, "main_amount");
        Pair p2 = new Pair<>(totalAmount, "total_amount");
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
