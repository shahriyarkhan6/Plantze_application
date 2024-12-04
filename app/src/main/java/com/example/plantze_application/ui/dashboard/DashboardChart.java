package com.example.plantze_application.ui.dashboard;

import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DashboardChart {
    Chart chart;
    Chart piechart;
    DashboardFirebase db;

    public DashboardChart(LineChart c, PieChart p){
        chart = c;
        piechart = p;
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
        yaxis.setAxisMaximum(c.getData().getYMax() + 100);
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        xaxis.setAxisMinimum(1);
        xaxis.setAxisMaximum(range);
        c.animateXY(1500, 1500, Easing.EaseOutBack);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void insertLineChartData(ArrayList<Entry> entries, int range, ChronoUnit timescale){
        entries.sort(new EntryXComparator());
        // create chart:
        LineChart emissionsLineChart = (LineChart)chart;
        LineDataSet chart_data = new LineDataSet(entries, "CO2 Emissions in KGs over "
                + range + " " + timescale.toString());
        chart_data.setLineWidth(4f);
        LineData c_data = new LineData(chart_data);
        c_data.setDrawValues(true);
        emissionsLineChart.setData(c_data);

        formatLineChart(emissionsLineChart, range);
        emissionsLineChart.invalidate();
    }
    private void insertPieChartData(ArrayList<PieEntry> categories, float totalEmissions){
        PieDataSet p = new PieDataSet(categories, "category");
        Log.d("DashboardChart","Consumption:" + categories.get(0).getY());
        PieChart pc = (PieChart)piechart;
        pc.setDrawHoleEnabled(true);
        pc.setHoleColor(Color.parseColor("#D8DBE2"));
        pc.setHoleRadius(50f);
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#009999"));
        colors.add(Color.parseColor("#373f51"));
        p.setColors(colors);
        PieData category= new PieData(p);
        category.setValueFormatter(new PercentFormatter());
        category.setValueTextColors(colors);
        category.setValueTextSize(8f);
        pc.setData(category);

        //format legend
        Legend l = pc.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setXOffset(150f);
        l.setDrawInside(false);


        pc.getDescription().setEnabled(false);
        pc.setCenterText("Total Emissions: " + totalEmissions);
        pc.setUsePercentValues(true);
        pc.animateY(1500, Easing.EaseInQuad);
        pc.invalidate();

    }
    private void clearCharts(){
        if(!chart.isEmpty()){
            if(chart.getData() != null) {
                chart.getData().clearValues();
            }
            chart.notifyDataSetChanged();
            chart.clear();
        }
        if(!piechart.isEmpty()){
            if(piechart.getData() != null) {
                piechart.getData().clearValues();
            }
            piechart.clear();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void collectWeek() {
        clearCharts();

        LocalDate currentDate = LocalDate.now();
        db.getC02ForDays(currentDate, 7,
                ChronoUnit.DAYS , new GetUserDataCallback() {
            @Override
            public void onCallback(ArrayList<Entry> week, ArrayList<PieEntry> pieWeek) {
                for(int i = 0; i < 7; i++){
                    Log.d("Dashboard Chart", "Entry: " + week.get(i));
                }
                float totalEmissions = week.get(week.size() - 1).getY();
                insertLineChartData(week, 7, ChronoUnit.DAYS);
                insertPieChartData(pieWeek, totalEmissions);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void collectMonth(){
        clearCharts();
        LocalDate currentDate = LocalDate.now();
        int monthLen = currentDate.lengthOfMonth();
        db.getC02ForDays(currentDate, monthLen,
                ChronoUnit.DAYS, new GetUserDataCallback() {
            @Override
            public void onCallback(ArrayList<Entry> month, ArrayList<PieEntry> pieMonth) {
                insertLineChartData(month, monthLen, ChronoUnit.DAYS);
                float totalEmissions = month.get(month.size() - 1).getY();
                insertPieChartData(pieMonth, totalEmissions);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void collectYear(){
        clearCharts();
        LocalDate currentDate = LocalDate.now();
        int yearLen = currentDate.lengthOfYear();
        db.getC02ForDays(currentDate, yearLen,
                ChronoUnit.MONTHS, new GetUserDataCallback() {
                    @Override
                    public void onCallback(ArrayList<Entry> year, ArrayList<PieEntry> pieYear) {
                        insertLineChartData(year, 12, ChronoUnit.MONTHS);
                        float totalEmissions = year.get(year.size() - 1).getY();
                        insertPieChartData(pieYear, totalEmissions);
                    }
                });
    }


}
