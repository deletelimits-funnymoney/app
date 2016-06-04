package de.deletelimits.funnymoney.ui.main;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.FactFactory;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.service.pojos.Fact;
import de.deletelimits.funnymoney.ui.main.base.BaseFragment;

public class PieChartFragment extends BaseFragment {

    @BindView(R.id.transactions_detail_piechart)
    PieChart pieChart;

    @Inject
    PostbankAPI postbankAPI;

    private HashMap<String, Double> groupAmounts;
    private double sum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.piechart, container, false);
        ButterKnife.bind(this, view);
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("Transaction Types");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(35);
        pieChart.setTransparentCircleRadius(45);

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);

        FactFactory f = new FactFactory(postbankAPI);
        Calendar start = Calendar.getInstance();
        start.set(2016, 4, 0);
        Calendar end = Calendar.getInstance();
        end.set(2016, 5, 0);

        Set<String> groups = f.collectGroups(start.getTime(), end.getTime());
        List<Fact> facts = f.groupTransactionsByCostType(start.getTime(), end.getTime());
        groupAmounts = new HashMap<>();
        for (String group : groups) {
            groupAmounts.put(group, 0.0);
        }
        for (Fact fact : facts) {
            for (Map.Entry<String, Double> stringDoubleEntry : fact.amounts.entrySet()) {
                groupAmounts.put(stringDoubleEntry.getKey(), groupAmounts.get(stringDoubleEntry.getKey()) + stringDoubleEntry.getValue());
            }
        }
        sum = 0;
        for (Map.Entry<String, Double> stringDoubleEntry : groupAmounts.entrySet()) {
            sum += stringDoubleEntry.getValue();
        }

        addData(facts);
        return view;
    }


    private void addData(List<Fact> facts) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        int i = 0;
        for (Map.Entry<String, Double> stringDoubleEntry : groupAmounts.entrySet()) {
            i++;
            yVals1.add(new Entry((float) (stringDoubleEntry.getValue().floatValue() / sum), i));
            xVals.add(stringDoubleEntry.getKey());
        }
        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "transaction types");
        dataSet.setSliceSpace(1);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();


        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        // update pie chart
        pieChart.invalidate();
    }
}
