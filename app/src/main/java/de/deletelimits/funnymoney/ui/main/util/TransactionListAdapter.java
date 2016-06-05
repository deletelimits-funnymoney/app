package de.deletelimits.funnymoney.ui.main.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.pojos.TransactionMapping;
import de.deletelimits.funnymoney.ui.main.TransactionDetailActivity;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionListItemViewHolder>{

    private static DateFormat dayFormatter = new SimpleDateFormat("d", Locale.GERMAN);
    private static DateFormat monthFormatter = new SimpleDateFormat("MMM", Locale.GERMAN);
    private final int greenColor;
    private final int redColor;
    private final Drawable fixedDrawable;
    private final Drawable variableDrawable;
    private List<TransactionMapping> transactions;
    private Activity mActivity;

    public TransactionListAdapter(Activity activity, List<TransactionMapping> transactions, Context context) {
        mActivity = activity;
        this.greenColor = ContextCompat.getColor(context, R.color.primary);
        this.redColor = ContextCompat.getColor(context, R.color.red);
        this.transactions = transactions;
        this.fixedDrawable = ContextCompat.getDrawable(context, R.drawable.rounded_background_fix);
        this.variableDrawable = ContextCompat.getDrawable(context, R.drawable.rounded_background_variable);
    }

    @Override
    public TransactionListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.transaction_list_item, null);
        return new TransactionListItemViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(TransactionListItemViewHolder holder, int position) {
        holder.setData(transactions.get(position));

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class TransactionListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.transaction_list_item_day)
        TextView dateDay;
        @BindView(R.id.transaction_list_item_month)
        TextView dateMonth;
        @BindView(R.id.transaction_list_item_reason)
        TextView reason;
        @BindView(R.id.transaction_list_item_reference_name)
        TextView referenceName;
        @BindView(R.id.transaction_list_item_amount)
        TextView amount;

        TransactionMapping transaction;

        @BindView(R.id.transaction_list_item_type)
        TextView type;
        @BindView(R.id.transaction_list_item_category)
        TextView category;

        public TransactionListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public void setData(TransactionMapping transactionMapping) {
            transaction = transactionMapping;

            dateDay.setText(String.valueOf(dayFormatter.format(new Date(transactionMapping.transaction.bookingDate))));
            dateMonth.setText(String.valueOf(monthFormatter.format(new Date(transactionMapping.transaction.bookingDate))));
            reason.setText(TextUtils.join(" ", transactionMapping.transaction.purpose.toArray()));
            referenceName.setText(transactionMapping.transaction.reference.accountHolder);
            amount.setTextColor((transactionMapping.transaction.amount.charAt(0) == '-') ? redColor : greenColor);
            amount.setText(transactionMapping.transaction.amount + " €");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                amount.setTransitionName("transaction_amount" + transactionMapping.transaction.transactionId);
                referenceName.setTransitionName("transaction_referenceName" + transactionMapping.transaction.transactionId);
            }
            int paddingLeft = type.getPaddingLeft();
            int paddingRight = type.getPaddingRight();
            int paddingBottom = type.getPaddingBottom();
            int paddingTop = type.getPaddingTop();
            if (transactionMapping.classification.cost_type.equals("fixed")) {
                type.setBackground(fixedDrawable);
            } else {
                type.setBackground(variableDrawable);
            }
            type.setText(transactionMapping.classification.cost_type);
            type.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            category.setText(transactionMapping.classification.group);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mActivity, TransactionDetailActivity.class);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                intent.putExtra("amount_transition_name",amount.getTransitionName());
                intent.putExtra("referenceName_transition_name",referenceName.getTransitionName());
            }

            intent.putExtra("amount",amount.getText());
            intent.putExtra("referenceName",referenceName.getText());
            intent.putExtra("reason",reason.getText());

            String transactionString = transaction.toJson();
            intent.putExtra("transaction",transactionString);

            Pair p1 = new Pair<>(amount, amount.getTransitionName());
            Pair p2 = new Pair<>(referenceName, referenceName.getTransitionName());
            View cardView = mActivity.findViewById(R.id.card_view);
            Pair p3 = new Pair<>(cardView, cardView.getTransitionName());

            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(mActivity, p1, p2, p3);
            mActivity.startActivity(intent, options.toBundle());
        }
    }
}
