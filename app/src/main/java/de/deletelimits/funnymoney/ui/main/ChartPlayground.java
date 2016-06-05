package de.deletelimits.funnymoney.ui.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.service.FactFactory;
import de.deletelimits.funnymoney.service.PostbankAPI;
import de.deletelimits.funnymoney.service.pojos.Fact;
import de.deletelimits.funnymoney.ui.main.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartPlayground extends BaseFragment {

    @BindView(R.id.chart)
    BarChart chart;

    @Inject
    PostbankAPI api;

    private static final String TAG = "ChartPlayground";

    private Random rand = new Random();
    private String[] months = {"j", "f", "m", "a", "m", "j", "j", "a", "s", "o", "n", "d"};

    public ChartPlayground() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_playground, container, false);
        ButterKnife.bind(this, view);
        setStyling();
        chart.invalidate();

        List<String> labels = new ArrayList<>();
        for (int i=0; i<30; i++) {
            labels.add("-"+String.valueOf(30-i));
        }

        Calendar periodStartC = Calendar.getInstance();
        periodStartC.add(Calendar.DAY_OF_MONTH, -30);
        Date periodEnd = new Date();
        List<Fact> facts = new FactFactory(api).groupTransactionsByGroup(periodStartC.getTime(), periodEnd);
//        Set<String> keys = facts.get(0).amounts.keySet();
//        for (String key : keys) {
//            valuesToDatasets()
//        }

        List<Float> comValues = new ArrayList<>();
        List<Float> apaValues = new ArrayList<>();
        List<Float> funValues = new ArrayList<>();
        for (int i=0; i<30; i++) {
            Map<String, Double> amounts = facts.get(i).amounts;
            float r = rand.nextFloat() * 100;
            comValues.add(-amounts.get("communication").floatValue());
            apaValues.add(-amounts.get("apartment").floatValue());
            funValues.add(r);
//            values.add((float) i*1000);
        }

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(valuesToDatasets(apaValues, "apartment", "FFA500"));
        dataSets.add(valuesToDatasets(comValues, "communication", "ff44dd"));
        dataSets.add(valuesToDatasets(funValues, "random values", "ffcc33"));
        BarData data = new BarData(labels, dataSets);
        chart.setData(data);
        chart.invalidate();
        return view;
    }

    private IBarDataSet valuesToDatasets(List<Float> values, String dataSetName, String color) {
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        for (int i=0; i<values.size(); i++) {
            barEntries.add(new BarEntry(values.get(i), i));
        }
        BarDataSet set1 = new BarDataSet(barEntries, dataSetName);
        set1.setColor(ColorTemplate.rgb(color));
        return set1;
    }

    private void setStyling() {
        chart.setDrawGridBackground(false);
        chart.setDescription("");
        chart.setDrawValueAboveBar(false);
        chart.setTouchEnabled(false);
        chart.setDrawValueAboveBar(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        chart.getAxisLeft().setEnabled(false);
    }
}
