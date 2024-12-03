package com.example.plantze_application.ui.dashboard;

import android.os.Build;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DashboardChart {
    Chart chart;
    DashboardFirebase db;
    public DashboardChart(LineChart c){
        chart = c;
        db = new DashboardFirebase();
    }
    private void formatLineChart(LineChart c, int range){
        c.getDescription().setEnabled(false);
        c.setDrawGridBackground(false);
        YAxis yaxis = c.getAxisLeft();
        c.getAxisRight().setEnabled(false);
        XAxis xaxis = c.getXAxis();

        //set domain
        yaxis.setAxisMinimum(0);
        yaxis.setAxisMinimum(c.getYChartMax() + 100);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setAxisMinimum(1);
        xaxis.setAxisMaximum(range);
        c.animateXY(1500, 1500, Easing.EaseOutBack);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void InsertLineChartData(ArrayList<Entry> entries, int range){
        entries.sort(new EntryXComparator());
        // create chart:
        LineChart emissionsLineChart = (LineChart)chart;
        LineDataSet chart_data = new LineDataSet(entries, "CO2 Emissions over time");
        LineData c_data = new LineData(chart_data);
        formatLineChart(emissionsLineChart, range);
        emissionsLineChart.setData(c_data);
        emissionsLineChart.invalidate();
    }
    private void clearChart(){
        if(!chart.isEmpty()){
            if(chart.getData() != null) {
                chart.getData().clearValues();
            }
            chart.notifyDataSetChanged();
            chart.clear();

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void collectWeek(TextView total) {
        clearChart();
        LocalDate currentDate = LocalDate.now();
        db.getC02ForDays(currentDate, total, 7,
                ChronoUnit.DAYS , new GetUserDataCallback() {
            @Override
            public void onCallback(ArrayList<Entry> week) {
                InsertLineChartData(week, 7);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void collectMonth(TextView total){
        clearChart();
        LocalDate currentDate = LocalDate.now();
        int monthLen = currentDate.lengthOfMonth();
        db.getC02ForDays(currentDate, total, monthLen,
                ChronoUnit.DAYS, new GetUserDataCallback() {
            @Override
            public void onCallback(ArrayList<Entry> month) {

                InsertLineChartData(month, monthLen);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void collectYear(TextView total){
        clearChart();
        LocalDate currentDate = LocalDate.now();
        int yearLen = currentDate.lengthOfYear();
        db.getC02ForDays(currentDate, total, yearLen,
                ChronoUnit.MONTHS, new GetUserDataCallback() {
                    @Override
                    public void onCallback(ArrayList<Entry> year) {
                        InsertLineChartData(year, yearLen);
                    }
                });
    }


}
