package com.example.linechartsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zgq.linechart.ChartView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ChartView chartView;
    //x轴坐标对应的数据
    private List<String> xValue = new ArrayList<>();
    //y轴坐标对应的数据
    private List<Integer> yValue = new ArrayList<>();
    //折线对应的数据
    private Map<String, Integer> value = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chartView = (ChartView) findViewById(R.id.line_chart);
        for (int i = 0; i < 8; i++) {
            xValue.add((i + 1) + "月");
            value.put((i + 1) + "月", (int) (Math.random() * 181 + 60));//60--240
        }

        for (int i = 0; i < 6; i++) {
            yValue.add(i * 60);
        }
        chartView.setValue(value, xValue, yValue);
    }
}
