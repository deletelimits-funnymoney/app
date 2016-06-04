package de.deletelimits.funnymoney.ui.main.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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
import de.deletelimits.funnymoney.service.pojos.Transaction;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionListItemViewHolder> {

    private static DateFormat dayFormatter = new SimpleDateFormat("d", Locale.GERMAN);
    private static DateFormat monthFormatter = new SimpleDateFormat("MMM", Locale.GERMAN);
    private final int greenColor;
    private final int redColor;

    private List<Transaction> transactions;

    public TransactionListAdapter(List<Transaction> transactions, Context context) {
        this.greenColor = ContextCompat.getColor(context, R.color.primary);
        this.redColor = ContextCompat.getColor(context, R.color.red);
        this.transactions = transactions;
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

    public class TransactionListItemViewHolder extends RecyclerView.ViewHolder {

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

        public TransactionListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(Transaction transaction) {
            dateDay.setText(String.valueOf(dayFormatter.format(new Date(transaction.bookingDate))));
            dateMonth.setText(String.valueOf(monthFormatter.format(new Date(transaction.bookingDate))));
            reason.setText(TextUtils.join(" ", transaction.purpose.toArray()));
            referenceName.setText(transaction.reference.accountHolder);
            amount.setTextColor((transaction.amount.charAt(0) == '-') ? redColor : greenColor);
            amount.setText(transaction.amount + " €");
        }
    }
}
