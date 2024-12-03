package com.example.plantze_application.ui.dashboard;

import com.github.mikephil.charting.charts.Chart;

public interface ChartStrategy {
    public void setText();
    public void setChart(Chart input);
    public void getWeek();
    public void getMonth();
    public void getYear();


}
