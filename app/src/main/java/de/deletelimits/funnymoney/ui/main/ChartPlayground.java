package de.deletelimits.funnymoney.ui.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.deletelimits.funnymoney.R;
import de.deletelimits.funnymoney.ui.main.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartPlayground extends BaseFragment {

    private Random rand = new Random();
    protected BarChart chart;
    private String[] months = {"j", "f", "m", "a", "m", "j", "j", "a", "s", "o", "n", "d"};

    public ChartPlayground() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chart_playground, container, false);
        chart = (BarChart) root.findViewById(R.id.chart);
        setStyling();
        chart.invalidate();

        List<String> labels = new ArrayList<>();
        for (int i=1; i<31; i++) {
            labels.add(String.valueOf(i));
        }
        List<Float> values = new ArrayList<>();
        for (int i=0; i<30; i++) {
            values.add((float) i*1000);
        }

        setData(labels, values);
        chart.invalidate();
        return root;
    }

    private List<IBarDataSet> valuesToDatasets(List<Float> values) {
        return valuesToDatasets(values, "data");
    }

    private List<IBarDataSet> valuesToDatasets(List<Float> values, String dataSetName) {
        ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
        for (int i=0; i<values.size(); i++) {
            barEntries.add(new BarEntry(values.get(i), i));
        }
        BarDataSet set1 = new BarDataSet(barEntries, dataSetName);
//        set1.setColor(R.color.primary);
        set1.setColor(ColorTemplate.rgb("00aa55"));
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        return dataSets;
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

    public void setData(List<String> xLabels, List<Float> values) {
        BarData data = new BarData(xLabels, valuesToDatasets(values));
        chart.setData(data);
    }
}
